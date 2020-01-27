package com.StockPharmacyProject.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface CartItemDAO {

        @Insert
        void insert(CartItem... items);

        @Query("UPDATE itemsOrder set quantity = :q  where itemId = :idItems")
        void update(int idItems,int q);

       @Update
        void update(CartItem items);


        @Query("DELETE FROM itemsOrder ")
        void deleteAll();



        @Query("DELETE FROM itemsOrder where itemId = :idItems")
        void delete(int idItems);

    @Query("DELETE FROM itemsOrder where id = :idItems")
    void deleteId(int idItems);


    @Query("SELECT * FROM itemsOrder")
        List<CartItem> getItems();




    }

