package com.example.blink;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.blink.database.AppDatabase;
import com.example.blink.database.entities.Category;
import com.example.blink.database.entities.Supplier;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchCustomer(View v){
        TextInputLayout usernameInputLayout = findViewById(R.id.userNameInputLayout);
        String username = String.valueOf(usernameInputLayout.getEditText().getText());

        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        List<Supplier> suppliers = db.supplierDao().GetAll();
        List<String> supplierNames = new ArrayList<>();

        for (Supplier supplier : suppliers) {
            supplierNames.add(supplier.name);
        }

        Intent intent;

        if (supplierNames.contains(username)) {
            intent = new Intent(this, ProviderMain.class);
            intent.putExtra(ProviderMain.usernameKey, username);
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