package com.example.blink.database.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.blink.database.entities.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM Product")
    List<Product> GetAll();

    @Query("SELECT * FROM Product WHERE" +
            " name LIKE '%' || :searchString || '%' AND " +
            "categoryName IN (:categories) AND " +
            "supplierName IN (:suppliers) ORDER BY " +
            "CASE WHEN :order = 'NameZA' THEN name END DESC, " +
            "CASE WHEN :order = 'Price09' THEN price END ASC, " +
            "CASE WHEN :order = 'Price90' THEN price END DESC, " +
            "CASE WHEN :order NOT IN ('NameZA', 'Price09', 'Price90') THEN name END ASC"

    )
    List<Product> GetWithNameLike(String searchString, String order, List<String> categories, List<String> suppliers);

    @Query("SELECT * FROM Product WHERE supplierName = :supplierName ORDER BY name")
    List<Product> GetProductsWithSupplier(String supplierName);

    @Query("Select productId FROM Product WHERE name = :name AND price = :price AND supplierName = :supplierName")
    Integer GetProductId(String name, double price, String supplierName);

    @Query("SELECT * FROM Product WHERE productId IN (:productIds)")
    List<Product> GetProductsWithIds(List<Integer> productIds);

    @Insert
    void Insert(List<Product> products);

    @Insert
    void Insert(Product product);
}

