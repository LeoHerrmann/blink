package com.example.blink.ui.provider;

import static androidx.navigation.Navigation.findNavController;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.blink.R;
import com.example.blink.database.AppDatabase;
import com.example.blink.database.entities.Product;
import com.example.blink.databinding.FragmentProviderProductsBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;
import java.util.Locale;

public class ProviderProductsFragment extends Fragment {

    FragmentProviderProductsBinding binding;

    ProviderActicityViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProviderProductsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel = new ViewModelProvider(getActivity()).get(ProviderActicityViewModel.class);

        setupNewProductButton();
        listProducts();

        return root;
    }

    private void setupNewProductButton() {
        binding.newProductButton.setOnClickListener(new View.OnClickListener() {
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
            ImageButton deleteButton = productView.findViewById(R.id.deleteButton);

            nameTextView.setText(product.name);
            priceTextView.setText(String.format(Locale.ENGLISH, "%.2f€", product.price));
            categoryTextView.setText(product.categoryName);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   openConfirmDeleteDialog(view);
                }
            });

            productContainer.addView(productView);
        }
    }

    private void openConfirmDeleteDialog(View view) {
        View productView = (View) view.getParent();
        TextView nameTextView = productView.findViewById(R.id.nameTextView);
        TextView priceTextView = productView.findViewById(R.id.priceTextView);

        String name = nameTextView.getText().toString();
        String price = priceTextView.getText().toString();
        String priceWithoutEuro = price.substring(0, price.length() - 1);
        String supplier = viewModel.providerName.getValue();

        AppDatabase db = AppDatabase.getInstance(getActivity().getApplicationContext());
        Integer productId = db.productDao().GetProductId(name, Double.parseDouble(priceWithoutEuro), supplier);

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        builder.setMessage("Sind sie sicher, dass sie das Produkt '" + name + "' löschen möchten?")
                .setTitle(R.string.delete_product)
                .setPositiveButton(R.string.delete_product, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteProduct(productId);
                        listProducts();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteProduct(Integer productId) {
        AppDatabase db = AppDatabase.getInstance(getActivity().getApplicationContext());
        db.productDao().Delete(productId);
        db.cartItemDao().DeleteByProductId(productId);
        db.favItemDao().DeleteByProductId(productId);
    }
}