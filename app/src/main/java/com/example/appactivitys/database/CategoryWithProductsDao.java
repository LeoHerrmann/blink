package com.example.appactivitys.database;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface CategoryWithProductsDao {
    @Transaction
    @Query("SELECT * FROM Category")
    public List<CategoryWithProducts> getCategoriesWithProducts();
}
