package com.example.blink.database.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.blink.database.entities.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM Product WHERE categoryName IN (:categories) AND supplierName IN (:suppliers)")
    public List<Product> GetAll(List<String> categories, List<String> suppliers);

    @Query("SELECT * FROM Product")
    public List<Product> GetAll();


    @Query("SELECT * FROM Product WHERE name LIKE '%' || :searchString || '%' AND categoryName IN (:categories) AND supplierName IN (:suppliers)")
    public List<Product> GetWithNameLike(String searchString, List<String> categories, List<String> suppliers);

    @Insert
    public void Insert(List<Product> products);
}

