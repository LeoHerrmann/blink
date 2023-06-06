package com.example.appactivitys.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Product {

    public Product(String name, double price, String categoryName, String supplierName) {
        this.name = name;
        this.price = price;
        this.categoryName = categoryName;
        this.supplierName = supplierName;

    }

    @PrimaryKey @NonNull
    public Integer productId;

    public String name;

    public double price;

    public String categoryName;

    public String supplierName;
}

