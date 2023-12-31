package com.example.blink.ui.provider;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.blink.R;
import com.example.blink.database.AppDatabase;
import com.example.blink.database.entities.Category;
import com.example.blink.database.entities.Product;
import com.example.blink.databinding.FragmentProviderAddProductBinding;

import java.util.ArrayList;
import java.util.List;

public class ProviderAddProductFragment extends Fragment {
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

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, categoryNames);
        AutoCompleteTextView textView = binding.categoryInput;
        textView.setAdapter(adapter);
    }

    private void setupAddProductButton() {
        binding.addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.productNameInput.getText().toString();
                String priceInput = binding.productPriceInput.getText().toString();
                String categoryName = binding.categoryInput.getText().toString();
                String supplierName = viewModel.providerName.getValue();

                if (name.isEmpty() || priceInput.isEmpty() || categoryName.isEmpty()) {
                    return;
                }

                Double price = Double.valueOf(priceInput);

                Product newProduct = new Product(name, price, categoryName, supplierName);

                AppDatabase db = AppDatabase.getInstance(getActivity().getApplicationContext());
                db.productDao().Insert(newProduct);

                NavController navController = findNavController(v);
                navController.navigate(R.id.action_providerAddProduct_to_productAddedFragment);
            }
        });
    }
}