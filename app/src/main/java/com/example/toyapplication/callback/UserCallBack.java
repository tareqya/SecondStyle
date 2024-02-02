package com.example.toyapplication.callback;

import com.google.android.gms.tasks.Task;

public interface UserCallBack {
    void onUserDataSaveComplete(Task<Void> task);
}
