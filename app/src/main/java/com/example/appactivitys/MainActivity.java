package com.example.appactivitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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