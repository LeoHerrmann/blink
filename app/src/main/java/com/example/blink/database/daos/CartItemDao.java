package com.example.blink.database.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.blink.database.entities.CartItem;
import java.util.List;

@Dao
public interface CartItemDao {
    @Query("SELECT * FROM CartItem")
    List<CartItem> GetAll();

    @Query("SELECT * FROM CartItem WHERE productId = :productId")
    CartItem GetByProductId(Integer productId);

    @Update
    void UpdateCartItem(CartItem cartItem);

    @Insert
    void Insert(CartItem cartItem);
}
