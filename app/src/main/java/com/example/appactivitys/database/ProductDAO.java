package com.example.appactivitys.database;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProductDAO {
    @Query("SELECT * FROM Product")
    List<Product> GetaAll();
}

