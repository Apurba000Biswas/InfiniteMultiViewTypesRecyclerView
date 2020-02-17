package com.apurba.infinitemultiviewtypesrecyclerview;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class VirtualRESTAPi {

    private static final int MAX_THRESHOLD = 20;
    private static final int DATABASE_SIZE = 150;

    private static List<DataItem> getDataBase(){
        List<DataItem> dataItems = new ArrayList<>();
        for (int i = 0; i< DATABASE_SIZE; i++){
            DataItem data = new DataItem("Apurba", i);
            dataItems.add(data);
        }
        return dataItems;
    }

    public static void getNextData(final int id , final VirtualApiResponseListener listener){
        int secondsDelayed = 1;
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<DataItem> database = getDataBase();
                int lastId = database.get(database.size()-1).getId();
                Log.d("virtual", "Request Id : " + id + "Last Id : " + lastId);

                if (id >= database.size()-1 || id >= lastId) {
                    listener.onApiResponse(null);
                    return;
                }

                List<DataItem> pageData = new ArrayList<>();
                int count = 0;
                int idCopy = id;
                while (idCopy < database.size() && count < MAX_THRESHOLD){
                    pageData.add(database.get(idCopy));
                    idCopy++;
                    count ++;
                }

                listener.onApiResponse(pageData);
            }
        }, 1000 * secondsDelayed);

    }

    public interface VirtualApiResponseListener{
        void onApiResponse(List<DataItem> pageData);
    }
}
