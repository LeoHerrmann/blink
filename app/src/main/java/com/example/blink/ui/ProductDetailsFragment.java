package com.example.blink.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.blink.R;
import com.example.blink.database.AppDatabase;
import com.example.blink.database.entities.CartItem;
import com.example.blink.database.entities.FavItem;
import com.example.blink.databinding.FragmentProductDetailsBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class ProductDetailsFragment extends Fragment {

    private FragmentProductDetailsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button addToCartButton = binding.addToCartButton;
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });


        AppDatabase db = AppDatabase.getInstance(requireContext().getApplicationContext());
        List<CartItem> favorits = db.cartItemDao().GetAll();
        ToggleButton toggleButton = binding.toggleButton;
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = toggleButton.isChecked();
                if (isChecked) {
                    Log.d("toggle", "onClick: true");
                    addToFav();

                } else {
                    Log.d("toggle", "onClick: false");
                }
            }


        });


        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        String price = getArguments().getString("price");
        String productName = getArguments().getString("productName");
        String supplierName = getArguments().getString("supplierName");
        String navigationOrigin = getArguments().getString("navigationOrigin");

        binding.priceTextView.setText(price);
        binding.supplierTextView.setText(supplierName);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(productName);

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.nav_view);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem item;

        if (navigationOrigin != null && navigationOrigin.equals("search")) {
            item = menu.findItem(R.id.customerSearchFragment);
        } else {
            item = menu.findItem(R.id.customerHomeFragment);
        }

        item.setChecked(true);
    }

    public void addToCart(){
        String countInput = binding.countInput.getText().toString();

        if (countInput.isEmpty()) {
            return;
        }

        int count = Integer.parseInt(countInput);

        String price = getArguments().getString("price");
        String priceWithoutEuro = price.substring(0, price.length() - 1);

        String productName = getArguments().getString("productName");
        String supplierName = getArguments().getString("supplierName");

        AppDatabase db = AppDatabase.getInstance(requireContext().getApplicationContext());
        Integer productId = db.productDao().GetProductId(productName, Double.parseDouble(priceWithoutEuro), supplierName);

        // Wir gucken, ob bereits ein Eintrag mit der Produkt-ID existiert
        CartItem existingCartItem = db.cartItemDao().GetByProductId(productId);

        if (existingCartItem == null) {
            // Wenn keiner existiert, dann erzeugen wir einen neuen Eintrag
            CartItem newCartItem = new CartItem(productId, count);
            db.cartItemDao().Insert(newCartItem);
        }
        else {
            // Wenn einer existiert, dann updaten wir diesen Eintrag, bzw. löschen ihn und erzeugen einen neuen
            existingCartItem.count += count;
            db.cartItemDao().UpdateCartItem(existingCartItem);
        }

        Button addToCartButton = binding.addToCartButton;
        addToCartButton.setText(R.string.added_to_cart);

        Handler handler = new Handler(Looper.getMainLooper());

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                addToCartButton.setText(R.string.add_to_cart);
            }
        }, 2000);
    }

    public void addToFav(){
        String price = getArguments().getString("price");
        String priceWithoutEuro = price.substring(0, price.length() - 1);

        String productName = getArguments().getString("productName");
        String supplierName = getArguments().getString("supplierName");

        AppDatabase db = AppDatabase.getInstance(requireContext().getApplicationContext());
        Integer productId = db.productDao().GetProductId(productName, Double.parseDouble(priceWithoutEuro), supplierName);

        FavItem newFavItem = new FavItem(productId);
    }

}