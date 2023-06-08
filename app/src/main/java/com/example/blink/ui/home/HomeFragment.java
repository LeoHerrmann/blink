package com.example.blink.ui.home;

import static androidx.navigation.Navigation.findNavController;

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

import com.example.blink.R;
import com.example.blink.database.AppDatabase;
import com.example.blink.database.entities.Category;
import com.example.blink.database.entities.Product;
import com.example.blink.databinding.FragmentHomeBinding;

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
            if (i % 23 == 1) {
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

        navController.navigate(R.id.action_navigation_home_to_productDetailsFragment, bundle);
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

    private void launchCategoryDetails(String categoryName){
        Log.d("test", categoryName);
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