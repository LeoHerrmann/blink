package com.example.blink.ui.customer;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.blink.R;
import com.example.blink.database.AppDatabase;
import com.example.blink.database.entities.Product;
import com.example.blink.databinding.FragmentCustomerFavoritesBinding;

import java.util.List;

public class FavoritesFragment extends Fragment {
    FragmentCustomerFavoritesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCustomerFavoritesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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

            nameTextView.setText(product.name);
            priceTextView.setText(String.format("%.2f€", product.price));
            supplierTextView.setText(product.supplierName);

            productView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    launchProductDetails(v);
                }
            });

            binding.favoritesContainer.addView(productView);
        }

        return root;
    }

    private void launchProductDetails(View v) {
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
        bundle.putString("navigationOrigin", "favorites");

        NavController navController = findNavController(v);
        navController.navigate(R.id.action_favoritesFragment_to_customerProductDetailsFragment, bundle);
    }
}