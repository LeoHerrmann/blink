package com.example.blink.ui.customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.blink.database.AppDatabase;
import com.example.blink.database.entities.Order;
import com.example.blink.databinding.FragmentCustomerOrdersBinding;

import java.util.List;

public class OrderFragment extends Fragment {

    private FragmentCustomerOrdersBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCustomerOrdersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        AppDatabase db = AppDatabase.getInstance(requireContext().getApplicationContext());
        List<Order> orders = db.orderDao().GetAll();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}