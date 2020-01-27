package com.StockPharmacyProject.data.local;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = CartItem.class, version = 2 , exportSchema = false )
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDatabase;
    public static synchronized AppDatabase getAppDatabase(Context context){
        if (appDatabase==null){
            appDatabase = Room.databaseBuilder(context, AppDatabase.class, "SofraDB")
                    .allowMainThreadQueries()
                    .build();
        }
     return appDatabase;
    }


    public abstract CartItemDAO getItemDAO();

}

