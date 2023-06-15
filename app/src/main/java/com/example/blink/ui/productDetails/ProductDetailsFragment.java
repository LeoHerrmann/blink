package com.example.blink.ui.productDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.blink.R;
import com.example.blink.databinding.FragmentProductDetailsBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProductDetailsFragment extends Fragment {

    private FragmentProductDetailsBinding binding;

    private static final String[] NUMBERS = new String[] {
            "1", "2", "3", "4", "5"
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, NUMBERS);
        AutoCompleteTextView textView = binding.autoCompleteTextView;
        textView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        String price = getArguments().getString("price");
        String productName = getArguments().getString("productName");
        String supplierName = getArguments().getString("supplierName");
        String navigationOrigin = getArguments().getString("navigationOrigin");

        binding.priceTextView.setText(price);
        binding.supplierTextView.setText(supplierName);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(productName);

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.nav_view);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem item;

        if (navigationOrigin == "search") {
            item = menu.findItem(R.id.navigation_search);
        } else {
            item = menu.findItem(R.id.navigation_home);
        }

        item.setChecked(true);
    }

}