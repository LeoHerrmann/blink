package com.example.blink.ui.provider;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.blink.databinding.FragmentProviderProductsBinding;

public class ProviderProductsFragment extends Fragment {

    FragmentProviderProductsBinding binding;

    ProviderActicityViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentProviderProductsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel = new ViewModelProvider(getActivity()).get(ProviderActicityViewModel.class);
        String providerName = viewModel.providerName.getValue();
        binding.textView.setText("Hallo " + providerName);

        // Inflate the layout for this fragment
        return root;
    }
}