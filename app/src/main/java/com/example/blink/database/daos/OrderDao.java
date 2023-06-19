package com.example.blink.database.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.blink.database.entities.Order;

import java.util.List;

@Dao
public interface OrderDao {
    @Query("SELECT * FROM `order`")
    List<Order> GetAll();

    @Insert
    void Insert(Order order);

    @Update
    void Update(Order order);

    @Query("SELECT * FROM `order` WHERE shippingMethod = :shippingMethod")
    List<Order> GetWithShipmentMethod(String shippingMethod);
}
