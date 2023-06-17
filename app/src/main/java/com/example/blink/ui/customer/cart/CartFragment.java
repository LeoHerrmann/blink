package com.example.blink.ui.customer.cart;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.blink.R;
import com.example.blink.database.AppDatabase;
import com.example.blink.database.entities.CartItem;
import com.example.blink.database.entities.Product;
import com.example.blink.databinding.FragmentCustomerCartBinding;

import java.util.List;

public class CartFragment extends Fragment {
    private FragmentCustomerCartBinding binding;
    private AppDatabase db;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCustomerCartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        db = AppDatabase.getInstance(requireContext().getApplicationContext());

        updateFragmentContent();

        return root;
    }

    private void initDeleteButton(int ID){
        db.cartItemDao().deleteCartItem(ID);
        updateFragmentContent();
    }

    private void updateFragmentContent() {
        List<CartItem> cartItems = db.cartItemDao().GetAll();
        List<Product> products = db.productDao().GetAll();
        double priceTotal = 0;

        binding.mainContent.removeAllViews();

        for (CartItem cartItem : cartItems) {
            String name = "";
            String price = "";
            String supplier = "";

            ImageButton deleteButton;
            View cartItemView = getLayoutInflater().inflate(R.layout.sample_customer_cart_product_view, null);

            for (Product product : products) {
                if (product.productId.equals(cartItem.productId)) {
                    name = product.name;
                    price = String.format("%.2f€", product.price);
                    supplier = product.supplierName;
                    priceTotal = priceTotal + (product.price * cartItem.count);

                    deleteButton = cartItemView.findViewById(R.id.removeFromCart);
                    deleteButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            initDeleteButton(cartItem.cartItemId);
                        }
                    });
                }
            }

            TextView nameTextView = cartItemView.findViewById(R.id.productName);
            TextView priceTextView = cartItemView.findViewById(R.id.productPrice);
            TextView supplierTextView = cartItemView.findViewById(R.id.productSupplier);
            EditText countInput = cartItemView.findViewById(R.id.countInput);

            nameTextView.setText(name);
            priceTextView.setText(price);
            supplierTextView.setText(supplier);

            countInput.setText(cartItem.count.toString());

            countInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // Vor dem Ändern des Textes (nicht benötigt)
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // Während des Ändern des Textes (nicht benötigt)
                }

                @Override
                public void afterTextChanged(Editable s) {
                    String inputText = s.toString();
                    if (!inputText.isEmpty()) {
                        int newCount = Integer.parseInt(inputText);
                        cartItem.count = newCount;
                        db.cartItemDao().updateCount(cartItem.cartItemId, newCount);

                        if(newCount == 0){
                            db.cartItemDao().deleteCartItem(cartItem.cartItemId);
                        }

                        updateFragmentContent();
                    }


                }
            });

            binding.mainContent.addView(cartItemView);
        }

        binding.checkoutButton.setText(getString(R.string.sum) + String.format("%.2f€", priceTotal) + " - " + getString(R.string.checkout));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
