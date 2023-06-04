package com.example.appactivitys.database;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface ProductDAO {
    @Query("SELECT * FROM Product")
    //public Single<List<Product>> GetaAll();
    public List<Product> GetAll();
}

