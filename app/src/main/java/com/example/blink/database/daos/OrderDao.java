package com.example.blink.database.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.blink.database.entities.Order;
import java.util.List;

@Dao
public interface OrderDao {
    @Query("SELECT * FROM `order` ORDER BY orderId DESC")
    List<Order> GetAll();

    @Query("SELECT * FROM `order` WHERE shippingMethod = :shippingMethod ORDER BY orderId DESC")
    List<Order> GetWithShipmentMethod(String shippingMethod);

    @Insert
    void Insert(Order order);

    @Update
    void Update(Order order);
}
