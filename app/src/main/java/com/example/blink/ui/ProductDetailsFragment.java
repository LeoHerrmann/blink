package com.example.blink.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.blink.R;
import com.example.blink.databinding.FragmentProductDetailsBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProductDetailsFragment extends Fragment {

    private FragmentProductDetailsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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