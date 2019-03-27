package com.fatesg.senaigo.retrofit2.bootstrap;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {


    private static final String ENDPOINT = "https://jsonplaceholder.typicode.com";

    public static Retrofit getClientUsers(){

        Retrofit retrofit;
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT.concat("/users/"))
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient).build();

        return retrofit;
    }

    public static Retrofit getClientPost(){

        Retrofit retrofit;
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT.concat("/posts/"))
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient).build();

        return retrofit;
    }
}
