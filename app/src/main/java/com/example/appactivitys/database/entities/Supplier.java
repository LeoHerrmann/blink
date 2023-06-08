package com.example.appactivitys.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Supplier {
    public Supplier(String name) {
        this.name = name;
    }

    @PrimaryKey
    @NonNull
    public String name;
}
