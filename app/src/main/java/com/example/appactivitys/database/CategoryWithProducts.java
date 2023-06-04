package com.example.appactivitys.database;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CategoryWithProducts {
    @Embedded public Category category;
    @Relation(
            parentColumn = "name",
            entityColumn = "categoryName"
    )
    public List<Product> products;
}
