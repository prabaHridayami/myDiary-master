package com.example.praba.prakmob.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static ApiService getService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.43.79/myDiary/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ApiService.class);
    }
}
