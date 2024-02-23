package com.example.toyapplication.callback;

import com.google.android.gms.tasks.Task;

public interface OrderCallBack {
    void onOrderAddComplete(Task<Void> task);
}
