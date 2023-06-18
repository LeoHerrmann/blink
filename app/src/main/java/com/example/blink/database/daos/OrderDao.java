package com.example.blink.database.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.blink.database.entities.Order;

import java.util.List;

@Dao
public interface OrderDao {
    @Query("SELECT * FROM `order`")
    List<Order> GetAll();

    @Insert
    void Insert(Order order);
}
