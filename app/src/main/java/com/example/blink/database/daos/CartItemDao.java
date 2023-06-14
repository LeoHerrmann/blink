package com.example.blink.database.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.blink.database.entities.CartItem;
import java.util.List;

@Dao
public interface CartItemDao {
    @Query("SELECT * FROM CartItem")
    List<CartItem> GetAll();

    @Insert
    void Insert(List<CartItem> cartItems);
}

