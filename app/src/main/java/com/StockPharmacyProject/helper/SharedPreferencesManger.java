package com.StockPharmacyProject.helper;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.StockPharmacyProject.data.modele.user.User.User_;

public class SharedPreferencesManger {

    private static SharedPreferences sharedPreferences = null;
    public static String PHARMACY_API_TOKEN = "PHARMACY_API_TOKEN";
    public static String PHARMACY_ID = "PHARMACY_ID";
    public static String PHARMACY_NAME = "PHARMACY_NAME";
    public static String PHARMACY_EMAIL = "PHARMACY_EMAIL";
    public static String PHARMACY_PASSWORD = "PHARMACY_PASSWORD";
    public static String IS_LOGN ="CHECK_BOX";
    public static String PHARMACY_PHONE = "PHARMACY_PHONE";



    public static String STOCK_API_TOKEN = "STOCK_API_TOKEN";
    public static String STOCK_ID = "STOCK_ID";
    public static String STOCK_NAME = "STOCK_NAME";
    public static String STOCK_PHONE = "STOCK_PHONE";
    public static String STOCK_EMAIL = "STOCK_EMAIL";
    public static String STOCK_PASSWORD = "STOCK_PASSWORD";
    public static String STOCK_ADDRESS = "STOCK_ADDRESS";
    public static Boolean STOCK_LOGIN ;
    public Context context;


    public static void setSharedPreferences(Activity activity) {
        if (sharedPreferences == null) {
            sharedPreferences = activity.getSharedPreferences("StockPharmacy", activity.MODE_PRIVATE);

        }
    }


    public static void SaveData(Activity activity, String data_Key, String data_Value) {
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(data_Key, data_Value);
            editor.commit();
        } else {
            setSharedPreferences(activity);
        }
    }


    public static void SaveData(Activity activity, String data_Key, boolean data_Value) {
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(data_Key, data_Value);
            editor.commit();
        } else {
            setSharedPreferences(activity);
        }
    }

    public static void SaveData(Activity activity, Boolean data_Key, boolean data_Value) {
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(String.valueOf(data_Key), data_Value);
            editor.commit();
        } else {
            setSharedPreferences(activity);
        }
    }
    public static boolean LoadBooleanData(Activity activity, String data_Key) {
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
        } else {
            setSharedPreferences(activity);
        }

        return sharedPreferences.getBoolean(data_Key, false);
    }



    public static String LoadData(Activity activity, String data_Key) {
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
        } else {
            setSharedPreferences(activity);
        }

        return sharedPreferences.getString(data_Key, null);
    }

    public static String LoadData1( String data_Key) {
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
        } else {

        }

        return sharedPreferences.getString(data_Key, null);
    }

    public static boolean LoadBoolean(Activity activity, String data_Key) {
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
        } else {
            setSharedPreferences(activity);
        }

        return sharedPreferences.getBoolean(data_Key, false);
    }
    
    public static void clean(Activity activity) {
        setSharedPreferences(activity);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
        }
    }

}
