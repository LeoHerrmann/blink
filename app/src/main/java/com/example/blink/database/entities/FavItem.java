package com.example.blink.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FavItem {
    public FavItem(Integer productId, Integer count)
    {
        this.count = count;
        this.productId = productId;
    }

    @PrimaryKey
    @NonNull
    public Integer favItemId;

    @NonNull
    public Integer count;

    @NonNull
    public Integer productId;
}

