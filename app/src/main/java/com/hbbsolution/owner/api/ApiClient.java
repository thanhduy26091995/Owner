package com.hbbsolution.owner.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by buivu on 04/05/2017.
 */

public class ApiClient {

    public static final String BASE_URL = "https://yukotest123.herokuapp.com/en/";
    public static String language = "vi";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
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
                .build();
        return okHttpClient;
    }

    public static void setLanguage(String lang) {
        language = lang;
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL + language + "/")
                .addConverterFactory(GsonConverterFactory.create())

                .build();
    }
}
