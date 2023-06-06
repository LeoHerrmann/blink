package com.example.appactivitys.ui.home;

import static androidx.navigation.Navigation.findNavController;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import com.example.appactivitys.R;
import com.example.appactivitys.database.AppDatabase;
import com.example.appactivitys.database.Category;
import com.example.appactivitys.database.Product;
import com.example.appactivitys.databinding.FragmentHomeBinding;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        AppDatabase db = AppDatabase.getInstance(requireContext().getApplicationContext());
        List<Category> categories = db.categoryDao().GetAll();

        for (Category category : categories) {
            addCategoryView(category.name);
        }

        List<Product> products = db.productDao().GetAll();

        for (int i = 0; i  < products.size(); i++) {
            if (i % 23 == 0) {
                addProductView(products.get(i));
            }
        }


        int displayWidth = getItemWidthInDP();
        int itemWidth = 130;
        int columnCount = displayWidth / itemWidth;

        GridLayout gridLayout = binding.categoriesGridLayout;
        gridLayout.setColumnCount(columnCount);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void launchProductDetails(View v) {
        NavController navController = findNavController(v);
        navController.navigate(R.id.action_navigation_home_to_productDetailsFragment);
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
    }

    private void addProductView(Product product) {
        double price = product.price;
        String priceString = String.valueOf(price) + "€"; //Bruder

        LinearLayout productContainer = binding.productContainer;
        View productView = getLayoutInflater().inflate(R.layout.sample_home_product_view, null); //Bruder
        TextView priceAndSupplierView = productView.findViewById(R.id.priceAndSupplierTextView);
        TextView nameView = productView.findViewById(R.id.nameTextView);

        priceAndSupplierView.setText(priceString + "  ·  " + product.supplierName);
        nameView.setText(product.name);

        productView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchProductDetails(v);
            }
        });

        productContainer.addView(productView);
    }
}