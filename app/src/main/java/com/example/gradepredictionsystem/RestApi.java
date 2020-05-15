package com.example.gradepredictionsystem;

import interfaces.JsonApiHolder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApi {
    public static JsonApiHolder getApi(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.18.9:33/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonApiHolder service = retrofit.create(JsonApiHolder.class);
        return service;
    }
}
