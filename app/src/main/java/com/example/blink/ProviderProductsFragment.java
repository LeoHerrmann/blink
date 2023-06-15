package com.example.blink;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.blink.databinding.FragmentProviderProductsBinding;
import com.example.blink.databinding.FragmentSearchBinding;

public class ProviderProductsFragment extends Fragment {

    FragmentProviderProductsBinding binding;

    ProviderMainViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentProviderProductsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel = new ViewModelProvider(getActivity()).get(ProviderMainViewModel.class);
        String providerName = viewModel.providerName.getValue();
        binding.textView.setText("Hallo " + providerName);

        // Inflate the layout for this fragment
        return root;
    }
}