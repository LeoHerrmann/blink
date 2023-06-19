package com.example.blink.database.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.blink.database.entities.FavItem;

import java.util.List;

@Dao
public interface FavItemDao {
    @Query("SELECT * FROM FavItem")
    List<FavItem> GetAll();

    @Query("SELECT * FROM FavItem WHERE productId = :productId")
    FavItem GetByProductId(Integer productId);

    @Query("DELETE FROM FavItem WHERE favItemId = :favItemId")
    void DeleteFavItem(Integer favItemId);

    @Query("DELETE FROM FavItem WHERE productId = :productId")
    void DeleteByProductId(Integer productId);

    @Query("DELETE FROM FavItem")
    void DeleteAll();

    @Update
    void UpdateFavItem(FavItem favItem);

    @Insert
    void Insert(FavItem favItem);
}

