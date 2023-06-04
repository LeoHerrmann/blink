package com.example.appactivitys.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.appactivitys.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;

@Database(entities = {Product.class, Category.class, Supplier.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDAO productDAO();
    public abstract CategoryDao categoryDao();

    public abstract SupplierDao supplierDao();

    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "database-name")
                .allowMainThreadQueries()
                .addCallback(new RoomDatabase.Callback() {
                    public void onCreate(SupportSQLiteDatabase db) {
                        super.onCreate(db);

                        Executors.newSingleThreadExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                AppDatabase appDatabase = AppDatabase.getInstance(context);
                                CategoryDao categoryDao = appDatabase.categoryDao();
                                SupplierDao supplierDao = appDatabase.supplierDao();

                                List<Category> categories = Arrays.asList(
                                        new Category("Brot & Brötchen"),
                                        new Category("Käse"),
                                        new Category("Kaffee"),
                                        new Category("Tee"),
                                        new Category("Nudeln & Reis")
                                );

                                List<Supplier> suppliers = Arrays.asList(
                                        new Supplier("Aldi Süd"),
                                        new Supplier("Aldi Nord"),
                                        new Supplier("Lidl"),
                                        new Supplier("Rewe"),
                                        new Supplier("Edeka"),
                                        new Supplier("Penny"),
                                        new Supplier("Netto")
                                );

                                categoryDao.Insert(categories);
                                supplierDao.Insert(suppliers);
                            }
                        });
                    }
                }).build();

            /*instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "database-name")
                    .allowMainThreadQueries()
                    .createFromAsset("raw/database.db")
                    .build();*/
        }
        return instance;
    }
}
