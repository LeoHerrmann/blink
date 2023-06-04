package com.example.appactivitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appactivitys.database.AppDatabase;
import com.google.android.material.textfield.TextInputLayout;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //List<Product> products = db.productDAO().GetAll();
        //List<Category> categories = db.categoryDao().GetAll();
        /*db.categoryDao().GetAll().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<Product>>() {
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