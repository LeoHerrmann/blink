package com.example.blink.ui.provider;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.blink.constants.OrderStatus;
import com.example.blink.constants.PaymentMethod;
import com.example.blink.R;
import com.example.blink.constants.ShippingMethod;
import com.example.blink.database.AppDatabase;
import com.example.blink.database.entities.Order;
import com.example.blink.databinding.FragmentProviderOrdersBinding;

import java.util.List;
import java.util.Locale;

public class ProviderOrdersFragment extends Fragment {
    private FragmentProviderOrdersBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProviderOrdersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        createOrderViews();

        return root;
    }

    private void createOrderViews() {
        binding.ordersContainer.removeAllViews();

        AppDatabase db = AppDatabase.getInstance(requireContext().getApplicationContext());
        List<Order> orders = db.orderDao().GetWithShipmentMethod(ShippingMethod.PickUp);

        TextView orderEmpty = binding.noOrders;
        if(orders.size() > 0){
            orderEmpty.setVisibility(View.GONE);
        } else {
            orderEmpty.setVisibility(View.VISIBLE);
        }

        for (Order order : orders) {
            View orderView = getLayoutInflater().inflate(R.layout.sample_provider_orders_order_view, binding.ordersContainer, false);

            TextView orderNumberTextView = orderView.findViewById(R.id.orderNumberTextView);
            TextView priceTextView = orderView.findViewById(R.id.priceTextView);
            TextView paymentMethodTextView = orderView.findViewById(R.id.paymentMethodTextView);
            Button statusButton = orderView.findViewById(R.id.statusButton);

            orderNumberTextView.setText(order.orderId.toString());
            priceTextView.setText(String.format(Locale.getDefault(), "%.2f€", order.price));
            paymentMethodTextView.setText(getPaymentMethodText(order.paymentMethod));
            statusButton.setText(getStatusButtonText(order.status));

            if (order.status.equals(OrderStatus.Submitted)) {
                statusButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        order.status = OrderStatus.ReadyForPickUp;
                        db.orderDao().Update(order);
                        createOrderViews();
                    }
                });
            }
            else if (order.status.equals(OrderStatus.ReadyForPickUp)) {
                statusButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        order.status = OrderStatus.Completed;
                        db.orderDao().Update(order);
                        createOrderViews();
                    }
                });
            }
            else if (order.status.equals(OrderStatus.Completed)) {
                statusButton.setEnabled(false);
            }

            binding.ordersContainer.addView(orderView);
        }
    }

    private String getPaymentMethodText(String paymentMethod) {
        if (paymentMethod.equals(PaymentMethod.Card)) {
            return getString(R.string.ec_card);
        }
        else if (paymentMethod.equals(PaymentMethod.Cash)) {
            return getString(R.string.cash);
        }
        else if (paymentMethod.equals(PaymentMethod.HeyPay)) {
            return getString(R.string.heypay);
        }

        return getString(R.string.ec_card);
    }

    private String getStatusButtonText(String status) {
        if (status.equals(OrderStatus.Submitted)) {
            return getString(R.string.markAsReady);
        }
        else if (status.equals(OrderStatus.ReadyForPickUp)) {
            return getString(R.string.complete);
        }
        else if (status.equals(OrderStatus.Completed)) {
            return getString(R.string.completed);
        }

        return getString(R.string.markAsReady);
    }
}