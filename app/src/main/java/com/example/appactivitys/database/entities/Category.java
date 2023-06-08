package com.example.appactivitys.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Category {
    public Category(String name) {
        this.name = name;
    }

    @PrimaryKey
    @NonNull
    public String name;
}

