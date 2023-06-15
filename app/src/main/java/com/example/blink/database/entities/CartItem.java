package com.example.blink.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CartItem {
    public CartItem(Integer productId, Integer count)
    {
        this.count = count;
        this.productId = productId;
    }

    @PrimaryKey
    @NonNull
    public Integer cartItemId;

    @NonNull
    public Integer count;

    @NonNull
    public Integer productId;
}

