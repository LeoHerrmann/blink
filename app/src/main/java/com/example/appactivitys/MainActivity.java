package com.example.appactivitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.appactivitys.database.AppDatabase;
import com.example.appactivitys.database.Category;
import com.example.appactivitys.database.CategoryDao;
import com.example.appactivitys.database.Product;
import com.example.appactivitys.database.ProductDAO;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        List<Product> products = db.productDAO().GetAll();
        List<Category> categories = db.categoryDao().GetAll();
        /*productDAO.GetaAll().subscribeOn(Schedulers.io()).observeOn(Schedulers.mainThread()).subscribe(new SingleObserver<List<Product>>() {
            @Override
            public void onSubscribe(Disposable d) {
                // Wird aufgerufen, wenn das Abonnement erstellt wurde
            }

            @Override
            public void onSuccess(List<Product> productList) {
                // Wird aufgerufen, wenn die Operation erfolgreich abgeschlossen wurde
                // productList enthält die Liste der Produkte
            }

            @Override
            public void onError(Throwable e) {
                // Wird aufgerufen, wenn ein Fehler aufgetreten ist
            }
        });
        db.close();*/
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