package com.apurba.infinitemultiviewtypesrecyclerview;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private AppRepository mAppRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = new AppRepository(application);
    }

    public LiveData<List<DataItem>> getCats() {
        return mAppRepository.getMutableLiveData(4);
    }

    public void loadNext(int num){
        mAppRepository.loadNextImages(num);
    }
}
