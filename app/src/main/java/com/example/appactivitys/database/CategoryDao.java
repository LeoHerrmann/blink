package com.example.appactivitys.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM category")
    //public Single<List<Category>> GetAll();
    public List<Category> GetAll();

    @Insert
    public void Insert(Category category);
}
