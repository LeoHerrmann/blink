package com.example.appactivitys.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM Product")
    public List<Product> GetAll();

    @Query("SELECT * FROM Product WHERE name LIKE '%' || :searchString || '%'")
    public List<Product> GetWithNameLike(String searchString);

    @Insert
    public void Insert(List<Product> products);
}

