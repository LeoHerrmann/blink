package com.example.blink.ui;

import android.graphics.drawable.Drawable;
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
import androidx.core.content.ContextCompat;
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
    AppDatabase db;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = AppDatabase.getInstance(requireContext().getApplicationContext());

        setupAddToCartButton();
        setupFavoriteButton();

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
        }
        else if (navigationOrigin != null && navigationOrigin.equals("favorites")) {
            item = menu.findItem(R.id.favoritesFragment);
        }
        else {
            item = menu.findItem(R.id.customerHomeFragment);
        }

        item.setChecked(true);
    }

    private void setupAddToCartButton() {
        Button addToCartButton = binding.addToCartButton;

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });
    }

    private void addToCart(){
        String countInput = binding.countInput.getText().toString();

        if (countInput.isEmpty()) {
            return;
        }

        int count = Integer.parseInt(countInput);

        Integer productId = getProductId();

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

    private void setupFavoriteButton() {
        Integer productId = getProductId();
        Button favoriteButton = binding.favoriteButton;

        FavItem favItem = db.favItemDao().GetByProductId(productId);
        boolean productIsFavItem = favItem != null;

        if (productIsFavItem) {
            binding.favoriteButton.setIconResource(R.drawable.baseline_star_24);
        }
        else {
            binding.favoriteButton.setIconResource(R.drawable.baseline_star_border_24);
        }

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavItem favItem = db.favItemDao().GetByProductId(productId);
                boolean productIsFavItem = favItem != null;

                if (productIsFavItem) {
                    removeFromFav();
                    binding.favoriteButton.setIconResource(R.drawable.baseline_star_border_24);

                } else {
                    addToFav();
                    binding.favoriteButton.setIconResource(R.drawable.baseline_star_24);
                }
            }
        });
    }

    private Integer getProductId() {
        String price = getArguments().getString("price");
        String priceWithoutEuro = price.substring(0, price.length() - 1);
        String productName = getArguments().getString("productName");
        String supplierName = getArguments().getString("supplierName");

        Integer productId = db.productDao().GetProductId(productName, Double.parseDouble(priceWithoutEuro), supplierName);

        return productId;
    }

    public void addToFav(){
        Integer productId = getProductId();
        FavItem newFavItem = new FavItem(productId);
        db.favItemDao().Insert(newFavItem);
    }

    public void removeFromFav(){
        Integer productId = getProductId();
        db.favItemDao().DeleteByProductId(productId);
    }
}