package com.example.see.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        System.out.println("hello 啊77");
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");



    }

    public LiveData<String> getText() {
        return mText;
    }
}