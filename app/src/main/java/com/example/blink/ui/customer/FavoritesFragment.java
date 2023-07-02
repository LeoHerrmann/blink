package com.example.blink.ui.customer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.blink.R;
import com.example.blink.database.AppDatabase;
import com.example.blink.database.entities.Product;
import com.example.blink.databinding.FragmentCustomerFavoritesBinding;

import java.util.List;
import java.util.Locale;

public class FavoritesFragment extends Fragment {
    FragmentCustomerFavoritesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCustomerFavoritesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        createFavoriteViews();

        return root;
    }

    private void createFavoriteViews() {
        binding.favoritesContainer.removeAllViews();

        AppDatabase db = AppDatabase.getInstance(requireContext().getApplicationContext());
        List<Product> products = db.productDao().GetFavoriteProducts();

        TextView noFavs = binding.noFavs;
        if(products.size() > 0){
            noFavs.setVisibility(View.GONE);
        } else {
            noFavs.setVisibility(View.VISIBLE);
        }

        for (Product product : products) {
            View productView = getLayoutInflater().inflate(R.layout.sample_customer_favorites_product_view, null);
            TextView nameTextView = productView.findViewById(R.id.nameTextView);
            TextView priceTextView = productView.findViewById(R.id.priceTextView);
            TextView supplierTextView = productView.findViewById(R.id.supplierTextView);
            ImageButton deleteButton = productView.findViewById(R.id.deleteButton);

            nameTextView.setText(product.name);
            priceTextView.setText(String.format(Locale.ENGLISH, "%.2f€", product.price));
            supplierTextView.setText(product.supplierName);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.favItemDao().DeleteByProductId(product.productId);
                    createFavoriteViews();
                }
            });

            productView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    launchProductDetails(product.productId);
                }
            });

            binding.favoritesContainer.addView(productView);
        }
    }

    private void launchProductDetails(int productId) {
        Bundle bundle = new Bundle();
        bundle.putInt("productId", productId);
        bundle.putString("navigationOrigin", "favorites");

        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.action_favoritesFragment_to_customerProductDetailsFragment, bundle);
    }
}