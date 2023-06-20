package com.example.blink.ui.customer;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import com.example.blink.CustomTextWatcher;
import com.example.blink.R;
import com.example.blink.database.AppDatabase;
import com.example.blink.database.entities.CartItem;
import com.example.blink.database.entities.Product;
import com.example.blink.databinding.FragmentCustomerCartBinding;

import java.util.List;
import java.util.Locale;

public class CartFragment extends Fragment {
    private CustomerActivityViewModel viewModel;
    private FragmentCustomerCartBinding binding;
    private AppDatabase db;
    private List<CartItem> cartItems;
    private List<Product> products;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCustomerCartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel = new ViewModelProvider(getActivity()).get(CustomerActivityViewModel.class);

        db = AppDatabase.getInstance(requireContext().getApplicationContext());

        setupCheckoutButton();
        updateFragmentContent();

        return root;
    }

    private void setupCheckoutButton() {
        binding.checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double totalPrice = calculateSum();
                viewModel.totalPriceOfCartItems.setValue(totalPrice);

                if (totalPrice == 0) {
                    return;
                }

                NavController navController = findNavController(v);
                navController.navigate(R.id.action_navigation_cart_to_customerCheckoutFragment);
            }
        });
    }

    private void initDeleteButton(int ID){
        db.cartItemDao().DeleteCartItem(ID);
        updateFragmentContent();
    }

    private void updateFragmentContent() {
        binding.mainContent.removeAllViews();

        cartItems = db.cartItemDao().GetAll();
        products = db.productDao().GetAll();

        TextView cartEmpty = binding.cartEmpty;
        if(cartItems.size() > 0){
            cartEmpty.setVisibility(View.GONE);
        } else {
            cartEmpty.setVisibility(View.VISIBLE);
        }

        for (CartItem cartItem : cartItems) {
            String name = "";
            String price = "";
            String supplier = "";

            View cartItemView = getLayoutInflater().inflate(R.layout.sample_customer_cart_product_view, null);

            for (Product product : products) {
                if (product.productId.equals(cartItem.productId)) {
                    name = product.name;
                    price = String.format(Locale.ENGLISH, "%.2f€", product.price);
                    supplier = product.supplierName;

                    Button deleteButton = cartItemView.findViewById(R.id.removeFromCart);
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

            Handler handler = new Handler(Looper.getMainLooper());

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    countInput.setText(cartItem.count.toString());
                }
            }, 500);

            countInput.addTextChangedListener(new CustomTextWatcher(countInput) {
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
                    updateCartDatabaseEntry(getEditTextView());
                    updateSum();
                }
            });

            binding.mainContent.addView(cartItemView);
        }

        updateSum();
    }

    private void updateCartDatabaseEntry(EditText editText) {
        EditText countView = editText;
        String input = countView.getText().toString();

        if (input.isEmpty()) {
            return;
        }

        View productView = (View) editText.getParent().getParent().getParent().getParent();
        TextView productNameView = productView.findViewById(R.id.productName);
        TextView productPriceView = productView.findViewById(R.id.productPrice);
        TextView productSupplierView = productView.findViewById(R.id.productSupplier);

        String name = productNameView.getText().toString();
        String priceAsString = productPriceView.getText().toString();
        String priceWithoutEuro = priceAsString.substring(0, priceAsString.length() - 1);
        String supplier = productSupplierView.getText().toString();

        Integer productId = db.productDao().GetProductId(name, Double.parseDouble(priceWithoutEuro), supplier);

        Integer count = Integer.valueOf(input);

        db.cartItemDao().UpdateCountByProductId(productId, count);
        cartItems = db.cartItemDao().GetAll();
    }

    private void updateSum() {
        double sum = calculateSum();
        setCheckoutButtonText(sum);
    }

    private double calculateSum() {
        double sum = 0;

        for (CartItem cartItem : cartItems) {
            for (Product product : products) {
                if (product.productId.equals(cartItem.productId)) {
                    sum += product.price * cartItem.count;
                }
            }
        }

        return sum;
    }

    private void setCheckoutButtonText(double price) {
        binding.checkoutButton.setText(
                getString(R.string.sum) +
                String.format(Locale.ENGLISH, "%.2f€", price) +
                " - " +
                getString(R.string.checkout)
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
