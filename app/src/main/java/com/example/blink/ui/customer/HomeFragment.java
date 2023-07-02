package com.example.blink.ui.customer;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.blink.R;
import com.example.blink.database.AppDatabase;
import com.example.blink.database.entities.Category;
import com.example.blink.database.entities.Product;
import com.example.blink.databinding.FragmentCustomerHomeBinding;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private FragmentCustomerHomeBinding binding;

    private CustomerActivityViewModel customerMainViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCustomerHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        customerMainViewModel = new ViewModelProvider(getActivity()).get(CustomerActivityViewModel.class);

        AppDatabase db = AppDatabase.getInstance(requireContext().getApplicationContext());
        List<Category> categories = db.categoryDao().GetAll();

        for (Category category : categories) {
            addCategoryView(category.name);
        }

        List<Product> products = db.productDao().GetAll();

        for (int i = 0; i  < products.size(); i++) {
            if (i % 23 == 5) {
                addProductView(products.get(i));
            }
        }

        initializeCategoriesGrid();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void addCategoryView(String categoryName) {
        GridLayout categoriesGridLayout = binding.categoriesGridLayout;
        View categoryView = getLayoutInflater().inflate(R.layout.sample_customer_home_category_view, null);
        TextView categoryTextView = categoryView.findViewById(R.id.categoryTextView);
        categoryTextView.setText(categoryName);
        GridLayout.LayoutParams gridParams = new GridLayout.LayoutParams();
        gridParams.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        gridParams.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        gridParams.width = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                125,
                getResources().getDisplayMetrics()
        );
        gridParams.height = GridLayout.LayoutParams.WRAP_CONTENT;

        categoryView.setLayoutParams(gridParams);
        categoriesGridLayout.addView(categoryView);

        ConstraintLayout categoryCardLayout = categoryView.findViewById(R.id.categoryLayout);

        categoryCardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCategoryDetails(categoryName);
            }
        });
    }

    private void addProductView(Product product) {
        LinearLayout productContainer = binding.productContainer;
        View productView = getLayoutInflater().inflate(R.layout.sample_customer_home_product_view, null); //Bruder
        TextView priceTextView = productView.findViewById(R.id.priceTextView);
        TextView supplierTextView = productView.findViewById(R.id.supplierTextView);
        TextView nameView = productView.findViewById(R.id.nameTextView);

        String priceString = String.format("%.2f€", product.price);
        priceTextView.setText(priceString);
        supplierTextView.setText(product.supplierName);
        nameView.setText(product.name);

        LinearLayout productViewLayout = productView.findViewById(R.id.productLayout);
        productViewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchProductDetails(product.productId);
            }
        });

        productContainer.addView(productView);
    }

    private void launchProductDetails(int productId) {
        Bundle bundle = new Bundle();
        bundle.putInt("productId", productId);

        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.action_navigation_home_to_productDetailsFragment, bundle);
    }

    private void launchCategoryDetails(String categoryName){
        Log.d("test", categoryName);
        ArrayList<String> selectedCategories = new ArrayList<String>();
        selectedCategories.add(categoryName);
        customerMainViewModel.selectedCategoryFilters.setValue(selectedCategories);

        NavigationBarView navView = getActivity().findViewById(R.id.nav_view);
        navView.setSelectedItemId(R.id.customerSearchFragment);
    }

    private void initializeCategoriesGrid() {
        int displayWidth = getItemWidthInDP();
        int itemWidth = 130;
        int columnCount = displayWidth / itemWidth;

        GridLayout gridLayout = binding.categoriesGridLayout;
        gridLayout.setColumnCount(columnCount);
    }

    private int getItemWidthInDP() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) requireContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidthInDp = (int) (displayMetrics.widthPixels / displayMetrics.density);
        return screenWidthInDp;
    }
}