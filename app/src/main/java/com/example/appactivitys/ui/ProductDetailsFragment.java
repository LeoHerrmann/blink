package com.example.appactivitys.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.appactivitys.R;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProductDetailsFragment extends Fragment {

    private ProductDetailsViewModel mViewModel;

    public static ProductDetailsFragment newInstance() {
        return new ProductDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_details, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.nav_view);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem item = menu.findItem(R.id.navigation_home);
        item.setChecked(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProductDetailsViewModel.class);
        // TODO: Use the ViewModel
    }

}