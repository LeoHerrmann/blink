package com.example.appactivitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.appactivitys.database.AppDatabase;
import com.example.appactivitys.database.Category;
import com.example.appactivitys.database.CategoryDao;
import com.example.appactivitys.database.Product;
import com.example.appactivitys.database.ProductDAO;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RoomDatabase.Callback rdc = new RoomDatabase.Callback() {
            public void onCreate (SupportSQLiteDatabase db) {
                //aus irgendeinem Grund wird das nicht ausgefeührt
                AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());
                CategoryDao categoryDao = appDatabase.categoryDao();
                Category c = new Category("testkategorie");
                categoryDao.Insert(c);
            }
        };

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-name").allowMainThreadQueries().fallbackToDestructiveMigration().addCallback(rdc).build();
        ProductDAO productDAO = db.productDAO();
        List<Product> products = productDAO.GetaAll();


    }

    public void launchCustomer(View v){
        TextInputLayout usernameInputLayout = findViewById(R.id.userNameInputLayout);
        String username = String.valueOf(usernameInputLayout.getEditText().getText());

        Intent intent;

        if (username.equals("provider")) {
            intent = new Intent(this, ProviderMain.class);
        }

        else if (username.equals("deliverer")) {
            intent = new Intent(this, DelivererMain.class);
        }

        else {
            intent = new Intent(this, CustomerMain.class);
        }

        startActivity(intent);
    }
}