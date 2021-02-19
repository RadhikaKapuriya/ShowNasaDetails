package com.example.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.Model.Post;
import com.example.repository.UserRepository;

import java.util.List;


public class MyApplication extends Application {

    private static Context appComponent;

    public static Context getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = getApplicationContext();
    }

}