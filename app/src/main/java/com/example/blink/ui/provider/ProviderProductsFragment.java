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
            View productView = getLayoutInflater().inflate(R.layout.sample_search_product_view, null);
            TextView nameTextView = productView.findViewById(R.id.nameTextView);
            TextView priceTextView = productView.findViewById(R.id.priceTextView);
            TextView supplierTextView = productView.findViewById(R.id.supplierTextView);

            nameTextView.setText(product.name);
            priceTextView.setText(getPriceString(product.price));
            supplierTextView.setText(product.categoryName);
            productView.setClickable(false);

            productContainer.addView(productView);
        }
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