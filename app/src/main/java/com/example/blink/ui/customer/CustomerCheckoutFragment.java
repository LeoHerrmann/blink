package com.example.blink.ui.customer;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.blink.R;
import com.example.blink.databinding.FragmentCustomerCheckoutBinding;

public class CustomerCheckoutFragment extends Fragment {

    private FragmentCustomerCheckoutBinding binding;
    private CustomerActivityViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCustomerCheckoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel = new ViewModelProvider(getActivity()).get(CustomerActivityViewModel.class);

        setupCompleteButton();

        return root;
    }

    private void setupCompleteButton() {
        Double price = viewModel.totalPriceOfCartItems.getValue();

        binding.completeOrderButton.setText(
                getString(R.string.sum) +
                String.format("%.2f€", price) +
                " - " +
                getString(R.string.complete_order)
        );

        binding.completeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Daten holen
                //Bestellung eintragen
                //Warenkorb leeren

                NavController navController = findNavController(v);
                navController.navigate(R.id.action_customerCheckoutFragment_to_customerCheckoutSuccessFragment);
            }
        });
    }
}