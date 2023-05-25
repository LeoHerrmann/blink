package com.example.appactivitys.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.appactivitys.R;
import com.example.appactivitys.databinding.FragmentHomeBinding;
import com.example.appactivitys.ui.ProductDetailsFragment;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ImageView imageView = root.findViewById(R.id.productImageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCustomerDetails(v);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void launchCustomerDetails(View v) {
        ProductDetailsFragment nextFragment = new ProductDetailsFragment();

        int idOfCurrentFragment = ((ViewGroup)getView().getParent()).getId();

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(idOfCurrentFragment, nextFragment, "findThisFragment")
                .addToBackStack(null)
                .commit();
    }


}