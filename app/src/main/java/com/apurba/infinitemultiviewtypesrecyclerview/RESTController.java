package com.apurba.infinitemultiviewtypesrecyclerview;

import android.util.Log;
import android.widget.GridLayout;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RESTController {

    private static final String THE_CAT_API_BASE_URL = "https://api.thecatapi.com";


    private static final String LOG_TAG = "RESTController";
    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static Retrofit retrofit;
    private static CatRESTApi catRESTApi;

    private static Retrofit getRetrofit(){
        if (retrofit == null) {
            synchronized (LOCK) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(THE_CAT_API_BASE_URL)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .build();
            }
        }
        return retrofit;
    }

    private static CatRESTApi getCatRESTApi(){
        if (catRESTApi == null) {
            synchronized (LOCK) {
                Retrofit retrofit = getRetrofit();
                catRESTApi = retrofit.create(CatRESTApi.class);
            }
        }
        return catRESTApi;
    }

    static void makeCatImageRequest(int num, TheCatApiResponseListener listener){
        CatRESTApi catsAPi = getCatRESTApi();
        Call<String> catsCall = catsAPi.catImageUrl(num);
        processCatsUrlResult(catsCall, listener);
    }

    private static void processCatsUrlResult(Call<String> catsCall, final TheCatApiResponseListener listener){
        catsCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String result = response.body().toString();
                processCatUrl(result, listener);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(LOG_TAG, "OnFailure : " + t.getMessage());
            }
        });
    }

    private static void processCatUrl(String jsonResult, TheCatApiResponseListener listener){

        try{
            JSONArray jsArray = new JSONArray(jsonResult);
            List<DataItem> dataItems = new ArrayList<>();
            for (int i=0; i<jsArray.length(); i++){
                String imgUrl = jsArray.getJSONObject(i).getString("url");
                dataItems.add(new DataItem(imgUrl, i));
            }
            listener.onApiResponse(dataItems);

        } catch (JSONException jsEx){
            Log.v(LOG_TAG,  "Parsing JSON" + jsEx.getMessage());
        }
    }

    public interface TheCatApiResponseListener{
        void onApiResponse(List<DataItem> pageData);
    }

}
