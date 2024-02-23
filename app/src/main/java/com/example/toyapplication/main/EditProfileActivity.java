package com.example.toyapplication.main;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.toyapplication.Database;
import com.example.toyapplication.R;
import com.example.toyapplication.callback.UserCallBack;
import com.example.toyapplication.information.User;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    private CircleImageView profileEdit_image;
    private FloatingActionButton profileEdit_FBTN_uploadImage;
    private TextInputLayout profileEdit_TF_firstName;
    private TextInputLayout profileEdit_TF_lastName;
    private TextInputLayout profileEdit_TF_phone;
    private Button editProfile_BTN_update;
    private User currentUser;
    private Uri selectedImageUri;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Intent intent = getIntent();
        currentUser = (User) intent.getSerializableExtra("USER");

        findViews();
        initVars();
        displayUser();
        if(!checkPermissions()){
            requestPermissions();
        }
    }

    private void displayUser() {
        profileEdit_TF_firstName.getEditText().setText(currentUser.getFirstName());
        profileEdit_TF_lastName.getEditText().setText(currentUser.getLastName());
        profileEdit_TF_phone.getEditText().setText(currentUser.getPhone());

        if(currentUser.getImageUrl() != null){
            Glide.with(this).load(currentUser.getImageUrl()).into(profileEdit_image);
        }

    }

    private void findViews() {
        profileEdit_image = findViewById(R.id.profileEdit_image);
        profileEdit_FBTN_uploadImage = findViewById(R.id.profileEdit_FBTN_uploadImage);
        profileEdit_TF_firstName = findViewById(R.id.profileEdit_TF_firstName);
        profileEdit_TF_lastName = findViewById(R.id.profileEdit_TF_lastName);
        profileEdit_TF_phone = findViewById(R.id.profileEdit_TF_phone);
        editProfile_BTN_update = findViewById(R.id.editProfile_BTN_update);

    }

    private void initVars() {
        database = new Database();
        database.setUserCallBack(new UserCallBack() {
            @Override
            public void onUserDataSaveComplete(Task<Void> task) {
                if(task.isSuccessful()){
                    finish();
                }else{
                    String err = task.getException().getMessage().toString();
                    Toast.makeText(EditProfileActivity.this, err, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void fetchUserDataComplete(User user) {

            }
        });
        profileEdit_FBTN_uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageSourceDialog();
            }
        });
        editProfile_BTN_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // update user data
                uploadUserData();
            }
        });
    }

    private void uploadUserData() {
        currentUser.setPhone(profileEdit_TF_phone.getEditText().getText().toString());
        currentUser.setFirstName(profileEdit_TF_firstName.getEditText().getText().toString());
        currentUser.setLastName(profileEdit_TF_lastName.getEditText().getText().toString());
        if(selectedImageUri != null){
            String imagePath = "Users/"+currentUser.getId()+"."+getFileExtension(selectedImageUri);
            boolean result = database.uploadImage(selectedImageUri, imagePath);
            if(result){
                currentUser.setImagePath(imagePath);
            }
        }

        database.saveUserData(currentUser);
    }

    public  boolean checkPermissions() {
        return (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                },
                100
        );
    }


    private void showImageSourceDialog() {
        CharSequence[] items = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
        builder.setTitle("Choose Image Source");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                switch (which) {
                    case 0:
                        openCamera();
                        break;
                    case 1:
                        openGallery();
                        break;
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraResults.launch(intent);
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        gallery_results.launch(intent);
    }

    private final ActivityResultLauncher<Intent> gallery_results = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    switch (result.getResultCode()) {
                        case Activity.RESULT_OK:
                            try {
                                Intent intent = result.getData();
                                selectedImageUri = intent.getData();
                                final InputStream imageStream = EditProfileActivity.this.getContentResolver().openInputStream(selectedImageUri);
                                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                                profileEdit_image.setImageBitmap(selectedImage);
                            }
                            catch (FileNotFoundException e) {
                                e.printStackTrace();
                                Toast.makeText(EditProfileActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                            }
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(EditProfileActivity.this, "Gallery canceled", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });

    private final ActivityResultLauncher<Intent> cameraResults = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    switch (result.getResultCode()) {
                        case Activity.RESULT_OK:
                            Intent intent = result.getData();
                            Bitmap bitmap = (Bitmap)  intent.getExtras().get("data");
                            profileEdit_image.setImageBitmap(bitmap);
                            selectedImageUri = getImageUri(EditProfileActivity.this, bitmap);
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(EditProfileActivity.this, "Camera canceled", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });


    public String getFileExtension(Uri uri){
        ContentResolver contentResolver = this.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    public Uri getImageUri(Activity activity, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(activity.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}