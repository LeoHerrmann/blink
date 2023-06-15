package com.example.blink;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.blink.databinding.ActivityCustomerMainBinding;
import com.example.blink.databinding.ActivityProviderMainBinding;

public class ProviderMain extends AppCompatActivity {

    public static final String usernameKey = "usernameKey";

    private ActivityProviderMainBinding binding;

    private ProviderMainViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ProviderMainViewModel.class);
        viewModel.providerName.setValue(getIntent().getStringExtra(usernameKey));

        binding = ActivityProviderMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.providerOrdersFragment, R.id.providerProductsFragment
        ).build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_provider_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }
}