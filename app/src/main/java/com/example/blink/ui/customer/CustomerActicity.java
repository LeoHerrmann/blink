package com.example.blink.ui.customer;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.blink.R;
import com.example.blink.database.AppDatabase;
import com.example.blink.database.entities.Category;
import com.example.blink.database.entities.Supplier;
import com.example.blink.databinding.ActivityCustomerBinding;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class CustomerActicity extends AppCompatActivity {

    private ActivityCustomerBinding binding;

    private NavController navController;

    private CustomerActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        List<Category> categories = db.categoryDao().GetAll();
        List<Supplier> suppliers = db.supplierDao().GetAll();

        ArrayList<String> selectedCategoryFilters = new ArrayList<>();
        ArrayList<String> selectedSupplierFilters = new ArrayList<>();

        for (Category category : categories) {
            selectedCategoryFilters.add(category.name);
        }

        for (Supplier supplier : suppliers) {
            selectedSupplierFilters.add(supplier.name);
        }

        viewModel = new ViewModelProvider(this).get(CustomerActivityViewModel.class);

        viewModel.selectedCategoryFilters.setValue(selectedCategoryFilters);
        viewModel.selectedSupplierFilters.setValue(selectedSupplierFilters);
        viewModel.selectedSortOrder.setValue("NameAZ");

        binding = ActivityCustomerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.customerHomeFragment, R.id.customerSearchFragment, R.id.customerCartFragment, R.id.customerOrdersFragment)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_customer_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            SearchView searchView = binding.searchView;

            if (navController.getCurrentDestination().getId() == R.id.customerSearchFragment) {
                searchView.setVisibility(View.VISIBLE);
            }
            else {
                searchView.setVisibility(View.GONE);
            }
        });

        binding.navView.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                int reselectedDestinationId = item.getItemId();
                navController.popBackStack(reselectedDestinationId, false);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_customer_main);
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}