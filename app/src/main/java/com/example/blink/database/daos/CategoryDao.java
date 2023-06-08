package com.example.blink.database.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.blink.database.entities.Category;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM category")
    public List<Category> GetAll();

    @Insert
    public void Insert(Category category);

    @Insert
    public void Insert(List<Category> categories);
}
