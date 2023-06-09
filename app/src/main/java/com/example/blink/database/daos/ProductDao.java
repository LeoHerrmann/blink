package com.example.blink.database.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.blink.database.entities.Product;

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

