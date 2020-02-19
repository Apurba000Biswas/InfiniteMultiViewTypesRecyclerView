package com.apurba.infinitemultiviewtypesrecyclerview;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CatRESTApi {

    @GET("/api/images/get?format=json")
    Call<String> catImageUrl(@Query("results_per_page") int num);
}
