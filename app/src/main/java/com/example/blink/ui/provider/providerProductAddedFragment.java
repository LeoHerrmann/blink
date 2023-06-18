package com.example.blink.ui.provider;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.blink.R;
import com.example.blink.databinding.FragmentProviderProductAddedBinding;

public class providerProductAddedFragment extends Fragment {
    FragmentProviderProductAddedBinding binding;
    ProviderActicityViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProviderProductAddedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel = new ViewModelProvider(getActivity()).get(ProviderActicityViewModel.class);

        setupNavigateButton();

        return root;
    }

    private void setupNavigateButton() {
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = findNavController(v);
                navController.navigate(R.id.action_productAddedFragment_to_providerProductsFragment);
            }
        });
    }
}