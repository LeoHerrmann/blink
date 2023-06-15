package com.example.blink.ui.customer.home;

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
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.example.blink.ui.customer.CustomerActivityViewModel;
import com.example.blink.R;
import com.example.blink.database.AppDatabase;
import com.example.blink.database.entities.Category;
import com.example.blink.database.entities.Product;
import com.example.blink.databinding.FragmentCustomerHomeBinding;

import java.util.ArrayList;
import java.util.List;

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

    public void launchProductDetails(View v) {
        TextView priceTextView = v.findViewById(R.id.priceTextView);
        TextView supplierTextView = v.findViewById(R.id.supplierTextView);
        TextView productTextView = v.findViewById(R.id.nameTextView);
        String price = (String) priceTextView.getText();
        String supplierName = (String) supplierTextView.getText();
        String productName = (String) productTextView.getText();

        Bundle bundle = new Bundle();
        bundle.putString("productName", productName);
        bundle.putString("supplierName", supplierName);
        bundle.putString("price", price);

        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.action_navigation_home_to_productDetailsFragment, bundle);
    }

    private void launchCategoryDetails(String categoryName){
        Log.d("test", categoryName);
        ArrayList<String> selectedCategories = new ArrayList<String>();
        selectedCategories.add(categoryName);
        customerMainViewModel.selectedCategoryFilters.setValue(selectedCategories);

        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.action_navigation_home_to_navigation_search, null, new NavOptions.Builder()
                .setPopUpTo(R.id.navigation_home, true)
                .build());
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

    private void addCategoryView(String categoryName) {
        GridLayout categoriesGridLayout = binding.categoriesGridLayout;
        View categoryView = getLayoutInflater().inflate(R.layout.sample_home_category_view, null);
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
        View productView = getLayoutInflater().inflate(R.layout.sample_home_product_view, null); //Bruder
        TextView priceTextView = productView.findViewById(R.id.priceTextView);
        TextView supplierTextView = productView.findViewById(R.id.supplierTextView);
        TextView nameView = productView.findViewById(R.id.nameTextView);

        String priceString = getPriceString(product.price);
        priceTextView.setText(priceString);
        supplierTextView.setText(product.supplierName);
        nameView.setText(product.name);

        LinearLayout productViewLayout = productView.findViewById(R.id.productLayout);
        productViewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchProductDetails(v);
            }
        });

        productContainer.addView(productView);
    }

    private String getPriceString(double price) {
        String priceString = String.valueOf(price);

        if (priceString.contains(".")) {
            // Wir trennen den String in zwei Teile: den Teil vor dem Punkt und den Teil danach
            String[] teile = priceString.split("\\.");

            // Überprüfen, ob der Teil nach dem Punkt weniger als zwei Stellen hat
            if (teile[1].length() < 2) {
                // Füge Nullen hinzu, um auf zwei Nachkommastellen zu kommen
                teile[1] = teile[1] + "0";
            }

            // Verbinde die Teile wieder zu einem String
            priceString = teile[0] + "." + teile[1];
        } else {
            // Wenn der String keinen Punkt enthält, fügen wir ".00" hinzu
            priceString = priceString + ".00";
        }

        priceString += "€";

        return priceString;
    }
}