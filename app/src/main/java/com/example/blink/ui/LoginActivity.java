package com.example.blink.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.blink.R;
import com.example.blink.database.AppDatabase;
import com.example.blink.database.entities.Supplier;
import com.example.blink.ui.customer.CustomerActicity;
import com.example.blink.ui.provider.ProviderActicity;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
            intent = new Intent(this, ProviderActicity.class);
            intent.putExtra(ProviderActicity.usernameKey, username);
        }

        else if (username.equals("deliverer")) {
            intent = new Intent(this, DelivererActivity.class);
        }

        else {
            intent = new Intent(this, CustomerActicity.class);
        }

        startActivity(intent);
    }
}