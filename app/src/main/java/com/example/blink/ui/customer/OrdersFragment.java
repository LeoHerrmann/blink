package com.example.blink.ui.customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.blink.constants.OrderStatus;
import com.example.blink.constants.PaymentMethod;
import com.example.blink.R;
import com.example.blink.constants.ShippingMethod;
import com.example.blink.database.AppDatabase;
import com.example.blink.database.entities.Order;
import com.example.blink.databinding.FragmentCustomerOrdersBinding;
import java.util.List;
import java.util.Locale;

public class OrdersFragment extends Fragment {

    private FragmentCustomerOrdersBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCustomerOrdersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        createOrderViews();

        return root;
    }

    private void createOrderViews() {
        AppDatabase db = AppDatabase.getInstance(requireContext().getApplicationContext());
        List<Order> orders = db.orderDao().GetAll();

        TextView orderEmpty = binding.emptyOrders;
        if(orders.size() > 0){
            orderEmpty.setVisibility(View.GONE);
        } else {
            orderEmpty.setVisibility(View.VISIBLE);
        }

        for (Order order : orders) {
            View orderView = getLayoutInflater().inflate(R.layout.sample_customer_orders_order_view, binding.ordersContainer, false);

            TextView orderNumberTextView = orderView.findViewById(R.id.orderNumberTextView);
            TextView priceTextView = orderView.findViewById(R.id.priceTextView);
            TextView shipmentMethodTextView = orderView.findViewById(R.id.shipmentMethodTextView);
            TextView paymentMethodTextView = orderView.findViewById(R.id.paymentMethodTextView);
            TextView statusChip = orderView.findViewById(R.id.statusChip);

            orderNumberTextView.setText(order.orderId.toString());
            priceTextView.setText(String.format(Locale.getDefault(), "%.2f€", order.price));
            shipmentMethodTextView.setText(getShipmentMethodText(order.shippingMethod));
            paymentMethodTextView.setText(getPaymentMethodText(order.paymentMethod));
            statusChip.setText(getStatusText(order.status));

            binding.ordersContainer.addView(orderView);
        }
    }

    private String getShipmentMethodText(String shippingMethod) {
        if (shippingMethod.equals(ShippingMethod.Delivery)) {
            return getString(R.string.delivery);
        }
        else if (shippingMethod.equals(ShippingMethod.PickUp)) {
            return getString(R.string.pick_up);
        }

        return getString(R.string.pick_up);
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

    private String getStatusText(String status) {
        if (status.equals(OrderStatus.Submitted)) {
            return getString(R.string.submitted);
        }
        else if (status.equals(OrderStatus.ReadyForPickUp)) {
            return getString(R.string.readyForPickup);
        }
        else if (status.equals(OrderStatus.Accepted)) {
            return getString(R.string.accepted);
        }
        else if (status.equals(OrderStatus.Completed)) {
            return getString(R.string.completed);
        }

        return getString(R.string.submitted);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}