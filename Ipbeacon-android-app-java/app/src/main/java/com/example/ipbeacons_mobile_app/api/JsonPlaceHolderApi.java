package com.example.ipbeacons_mobile_app.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {

    @GET("info/")
    Call<List<Info>>getInfos(@Query("beacon") String beaconName);

}
