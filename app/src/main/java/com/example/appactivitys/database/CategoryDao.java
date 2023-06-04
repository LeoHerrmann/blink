package com.example.appactivitys.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM category")
    public List<Category> GetAll();

    @Insert
    public void Insert(Category category);
}
