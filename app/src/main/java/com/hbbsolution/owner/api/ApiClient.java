package com.hbbsolution.owner.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by buivu on 04/05/2017.
 */

public class ApiClient {

    public static final String BASE_URL = "https://yukotest123.herokuapp.com/";
    public static String language = "en";
    public static String token = "0eb910010d0252eb04296d7dc32e657b402290755a85367e8b7a806c7e8bd14b0902e541763a67ef41f2dfb3b9b4919869b609e34dbf6bace4525fa6731d1046";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL + language + "/")
                    .client(okHttpClient())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    private static OkHttpClient okHttpClient() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        okhttp3.Request request = chain.request().newBuilder()
                                .addHeader("hbbgvauth", ApiClient.token).build();
                        return chain.proceed(request);

                    }
                })
                .build();

        return okHttpClient;
    }

    public static void setLanguage(String lang) {
        language = lang;
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL + language + "/")
                .client(okHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
