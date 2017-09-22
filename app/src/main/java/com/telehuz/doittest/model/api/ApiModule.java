package com.telehuz.doittest.model.api;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiModule {

    public static ApiInterface getApiInterface() {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://api.doitserver.in.ua/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

        return builder.build().create(ApiInterface.class);
    }

    public static ApiInterface getApiInterface(String token) {

        OkHttpClient httpClient = new OkHttpClient();

        httpClient.interceptors().add(chain -> {
                Request request = chain.request().newBuilder().addHeader("token", token).build();
                return chain.proceed(request);
        });

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://api.doitserver.in.ua/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(httpClient);

        return builder.build().create(ApiInterface.class);
    }
}
