package com.example.appactivitys.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Supplier {
    @PrimaryKey
    @NonNull
    public String name;
}
