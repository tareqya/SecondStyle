package com.example.toyapplication.callback;

import com.example.toyapplication.information.User;
import com.google.android.gms.tasks.Task;

public interface UserCallBack {
    void onUserDataSaveComplete(Task<Void> task);
    void fetchUserDataComplete(User user);
}
