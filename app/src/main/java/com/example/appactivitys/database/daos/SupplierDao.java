package com.example.appactivitys.database.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.appactivitys.database.entities.Supplier;

import java.util.List;

@Dao
public interface SupplierDao {
    @Query("SELECT * FROM supplier")
    public List<Supplier> GetAll();

    @Insert
    public void Insert(Supplier category);

    @Insert
    public void Insert(List<Supplier> categories);
}
