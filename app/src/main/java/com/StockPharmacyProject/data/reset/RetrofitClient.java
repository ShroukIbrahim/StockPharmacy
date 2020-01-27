package com.StockPharmacyProject.data.reset;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.util.Log;

import com.StockPharmacyProject.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

import static com.StockPharmacyProject.helper.SharedPreferencesManger.LoadData;
import static com.StockPharmacyProject.helper.SharedPreferencesManger.LoadData1;
import static com.StockPharmacyProject.helper.SharedPreferencesManger.PHARMACY_API_TOKEN;
import static okhttp3.internal.http.HttpDate.format;

public class RetrofitClient {

    public static String BASE_URL = "http://andrewsameeh-001-site1.itempurl.com/api/";
    public static Retrofit retrofit = null;


    public static Retrofit getClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder oktHttpClient = new OkHttpClient.Builder();
        oktHttpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                oktHttpClient.addInterceptor(logging);
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Authorization","Bearer "+ LoadData1(PHARMACY_API_TOKEN))
                        .method(original.method(), original.body())
                        .build();


                return chain.proceed(request);
            }
        });

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(oktHttpClient.build())
                    .build();



        return retrofit;
    }
}
