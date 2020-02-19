package com.apurba.infinitemultiviewtypesrecyclerview;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class AppRepository{

    private ArrayList<DataItem> movies = new ArrayList<>();
    private MutableLiveData<List<DataItem>> mutableLiveData = new MutableLiveData<>();
    private Application application;

    public AppRepository(Application application) {
        this.application = application;
    }

    MutableLiveData<List<DataItem>> getMutableLiveData(int num) {
        RESTController.getMutableLiveData(mutableLiveData, num);
        return mutableLiveData;
    }

    public void loadNextImages(int num){
        RESTController.getMutableLiveData(mutableLiveData, num);
    }
}
