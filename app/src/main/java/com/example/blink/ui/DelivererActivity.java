package com.example.blink.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.blink.OrderStatus;
import com.example.blink.PaymentMethod;
import com.example.blink.R;
import com.example.blink.ShippingMethod;
import com.example.blink.database.AppDatabase;
import com.example.blink.database.entities.Order;
import com.example.blink.databinding.ActivityDelivererBinding;

import java.util.List;

public class DelivererActivity extends AppCompatActivity {

    AppDatabase db;
    ActivityDelivererBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDelivererBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.auftraege);

        db = AppDatabase.getInstance(getApplicationContext());

        createOrderViews();
    }

    private void createOrderViews() {
        binding.ordersContainer.removeAllViews();

        List<Order> orders = db.orderDao().GetWithShipmentMethod(ShippingMethod.Delivery);

        TextView orderEmpty = binding.emptyDeliveries;
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
            priceTextView.setText(String.format("%.2f€", order.price));
            paymentMethodTextView.setText(getPaymentMethodText(order.paymentMethod));
            statusButton.setText(getStatusText(order.status));

            if (order.status.equals(OrderStatus.Submitted)) {
                statusButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        order.status = OrderStatus.Accepted;
                        db.orderDao().Update(order);
                        createOrderViews();
                    }
                });
            }
            else if (order.status.equals(OrderStatus.Accepted)) {
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

    private String getStatusText(String status) {
        if (status.equals(OrderStatus.Submitted)) {
            return getString(R.string.acceptOrder);
        }
        else if (status.equals(OrderStatus.Accepted)) {
            return getString(R.string.complete);
        }
        else if (status.equals(OrderStatus.Completed)) {
            return getString(R.string.completed);
        }

        return getString(R.string.acceptOrder);
    }
}