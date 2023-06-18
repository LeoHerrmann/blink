package com.example.blink.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Order {
    public Order(String shippingMethod, String paymentMethod, String status, Double price) {
        this.shippingMethod = shippingMethod;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.price = price;
    }

    @PrimaryKey
    @NonNull
    public Integer orderId;

    public String shippingMethod;

    public String paymentMethod;

    public String status;

    public Double price;
}

