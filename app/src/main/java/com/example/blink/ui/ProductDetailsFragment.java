package com.example.blink.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.blink.R;
import com.example.blink.database.AppDatabase;
import com.example.blink.database.entities.CartItem;
import com.example.blink.database.entities.FavItem;
import com.example.blink.database.entities.Product;
import com.example.blink.databinding.FragmentProductDetailsBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

public class ProductDetailsFragment extends Fragment {

    private FragmentProductDetailsBinding binding;
    AppDatabase db;
    Product product;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = AppDatabase.getInstance(requireContext().getApplicationContext());
        int productId = getArguments().getInt("productId");
        product = db.productDao().GetById(productId);

        setupAddToCartButton();
        setupFavoriteButton();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        String navigationOrigin = getArguments().getString("navigationOrigin");

        binding.priceTextView.setText(String.format("%.2f€", product.price));
        binding.supplierTextView.setText(product.supplierName);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(product.name);

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

        // Wir gucken, ob bereits ein Eintrag mit der Produkt-ID existiert
        CartItem existingCartItem = db.cartItemDao().GetByProductId(product.productId);

        if (existingCartItem == null) {
            // Wenn keiner existiert, dann erzeugen wir einen neuen Eintrag
            CartItem newCartItem = new CartItem(product.productId, count);
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
        Button favoriteButton = binding.favoriteButton;

        FavItem favItem = db.favItemDao().GetByProductId(product.productId);
        boolean productIsFavItem = favItem != null;

        if (productIsFavItem) {
            binding.favoriteButton.setIconResource(R.drawable.baseline_star_24);
            binding.favoriteButton.setText(R.string.aus_favoriten_entfernen);
        }
        else {
            binding.favoriteButton.setIconResource(R.drawable.baseline_star_border_24);
            binding.favoriteButton.setText(R.string.zu_favoriten_hinzufuegen);
        }

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavItem favItem = db.favItemDao().GetByProductId(product.productId);
                boolean productIsFavItem = favItem != null;

                if (productIsFavItem) {
                    removeFromFav();
                    binding.favoriteButton.setIconResource(R.drawable.baseline_star_border_24);
                    binding.favoriteButton.setText(R.string.zu_favoriten_hinzufuegen);

                } else {
                    addToFav();
                    binding.favoriteButton.setIconResource(R.drawable.baseline_star_24);
                    binding.favoriteButton.setText(R.string.aus_favoriten_entfernen);
                }
            }
        });
    }

    public void addToFav(){
        FavItem newFavItem = new FavItem(product.productId);
        db.favItemDao().Insert(newFavItem);
    }

    public void removeFromFav(){
        db.favItemDao().DeleteByProductId(product.productId);
    }
}