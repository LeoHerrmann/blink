package com.example.blink.ui.customer;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.blink.constants.OrderStatus;
import com.example.blink.constants.PaymentMethod;
import com.example.blink.R;
import com.example.blink.constants.ShippingMethod;
import com.example.blink.database.AppDatabase;
import com.example.blink.database.entities.Order;
import com.example.blink.databinding.FragmentCustomerCheckoutBinding;

import java.util.Locale;

public class CheckoutFragment extends Fragment {

    private FragmentCustomerCheckoutBinding binding;
    private CustomerActivityViewModel viewModel;
    private String selectedShippingMethod = ShippingMethod.PickUp;
    private String selectedPaymentMethod = PaymentMethod.Card;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCustomerCheckoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel = new ViewModelProvider(getActivity()).get(CustomerActivityViewModel.class);

        setupRadioButtons();
        setupCompleteButton();

        return root;
    }

    private void setupRadioButtons() {
        binding.shippingMethodRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioPickup) {
                    selectedShippingMethod = ShippingMethod.PickUp;
                }
                else if (checkedId == R.id.radioDeliver) {
                    selectedShippingMethod = ShippingMethod.Delivery;
                }
            }
        });

        binding.paymentMethodRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioCard) {
                    selectedPaymentMethod = PaymentMethod.Card;
                }
                else if (checkedId == R.id.radioCash) {
                    selectedPaymentMethod = PaymentMethod.Cash;
                }
                else if (checkedId == R.id.radioHeyPay) {
                    selectedPaymentMethod = PaymentMethod.HeyPay;
                }
            }
        });
    }

    private void setupCompleteButton() {
        Double price = viewModel.totalPriceOfCartItems.getValue();

        binding.completeOrderButton.setText(
                getString(R.string.sum) +
                        String.format(Locale.getDefault(), "%.2f€", price) +
                        " - " +
                        getString(R.string.complete_order)
        );

        binding.completeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = OrderStatus.Submitted;
                Double price = viewModel.totalPriceOfCartItems.getValue();
                Order order = new Order(selectedShippingMethod, selectedPaymentMethod, status, price);

                AppDatabase db = AppDatabase.getInstance(requireContext().getApplicationContext());
                db.orderDao().Insert(order);
                db.cartItemDao().DeleteAll();

                NavController navController = findNavController(v);
                navController.navigate(R.id.action_customerCheckoutFragment_to_customerCheckoutSuccessFragment);
            }
        });
    }
}