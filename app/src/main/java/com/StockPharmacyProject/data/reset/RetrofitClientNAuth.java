package com.StockPharmacyProject.data.reset;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClientNAuth {

    public static String BASE_URL = "http://andrewsameeh-001-site1.itempurl.com/api/";
    public static Retrofit retrofit = null;

    public static Retrofit getClientNAuth() {

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();



        return retrofit;
    }
}
