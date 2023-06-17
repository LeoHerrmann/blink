package com.example.blink.ui.provider;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.blink.R;
import com.example.blink.database.AppDatabase;
import com.example.blink.database.entities.Product;
import com.example.blink.databinding.FragmentProviderProductsBinding;

import java.util.List;

public class ProviderProductsFragment extends Fragment {

    FragmentProviderProductsBinding binding;

    ProviderActicityViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProviderProductsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel = new ViewModelProvider(getActivity()).get(ProviderActicityViewModel.class);

        setupFab();
        listProducts();

        return root;
    }

    private void setupFab() {
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = findNavController(v);
                navController.navigate(R.id.action_providerProductsFragment_to_providerAddProduct);
            }
        });
    }

    private void listProducts() {
        String supplierName = viewModel.providerName.getValue();

        AppDatabase db = AppDatabase.getInstance(requireContext().getApplicationContext());
        List<Product> products = db.productDao().GetProductsWithSupplier(supplierName);

        LinearLayout productContainer = binding.productContainer;
        productContainer.removeAllViews();

        for (Product product : products) {
            View productView = getLayoutInflater().inflate(R.layout.sample_provider_products_product_view, null);
            TextView nameTextView = productView.findViewById(R.id.nameTextView);
            TextView priceTextView = productView.findViewById(R.id.priceTextView);
            TextView categoryTextView = productView.findViewById(R.id.categoryTextView);

            nameTextView.setText(product.name);
            priceTextView.setText(String.format("%.2f€", product.price));
            categoryTextView.setText(product.categoryName);
            productView.setClickable(false);

            productContainer.addView(productView);
        }
    }
}