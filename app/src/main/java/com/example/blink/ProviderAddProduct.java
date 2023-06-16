package com.example.blink;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.blink.database.AppDatabase;
import com.example.blink.database.entities.Category;
import com.example.blink.database.entities.Product;
import com.example.blink.databinding.FragmentCustomerSearchBinding;
import com.example.blink.databinding.FragmentProviderAddProductBinding;
import com.example.blink.ui.customer.CustomerActivityViewModel;
import com.example.blink.ui.provider.ProviderActicityViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProviderAddProduct extends Fragment {
    FragmentProviderAddProductBinding binding;
    ProviderActicityViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProviderAddProductBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel = new ViewModelProvider(getActivity()).get(ProviderActicityViewModel.class);

        setupCategoryInput();
        setupAddProductButton();

        return root;
    }

    private void setupCategoryInput() {
        AppDatabase db = AppDatabase.getInstance(getActivity().getApplicationContext());
        List<Category> categories = db.categoryDao().GetAll();
        List<String> categoryNames = new ArrayList<>();

        for (Category category : categories) {
            categoryNames.add(category.name);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, categoryNames);
        AutoCompleteTextView textView = binding.categoryInput;
        textView.setAdapter(adapter);
    }

    private void setupAddProductButton() {
        binding.addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.productNameInput.getText().toString();
                Double price = Double.valueOf(binding.productPriceInput.getText().toString());
                String categoryName = binding.categoryInput.getText().toString();
                String supplierName = viewModel.providerName.getValue();

                Product newProduct = new Product(name, price, categoryName, supplierName);

                AppDatabase db = AppDatabase.getInstance(getActivity().getApplicationContext());
                db.productDao().Insert(newProduct);

                NavController navController = findNavController(v);
                navController.navigate(R.id.action_providerAddProduct_to_providerProductsFragment, null, new NavOptions.Builder()
                        .setPopUpTo(R.id.providerProductsFragment, true)
                        .build());
            }
        });
    }
}