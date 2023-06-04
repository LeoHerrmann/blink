package com.example.appactivitys.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

@Database(entities = {Product.class, Category.class, Supplier.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDAO productDAO();
    public abstract CategoryDao categoryDao();

    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
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
                                Category c = new Category("testkategorie");
                                categoryDao.Insert(c);
                                appDatabase.close();
                            }
                        });
                    }
                }).build();
        }
        return instance;
    }

     public static void destroyInstance() {
        instance = null;
     }
}
