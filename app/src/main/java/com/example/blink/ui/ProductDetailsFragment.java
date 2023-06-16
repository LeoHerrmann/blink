package com.example.blink.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.blink.R;
import com.example.blink.database.AppDatabase;
import com.example.blink.database.entities.CartItem;
import com.example.blink.database.entities.Category;
import com.example.blink.databinding.FragmentProductDetailsBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class ProductDetailsFragment extends Fragment {

    private FragmentProductDetailsBinding binding;

    private static final String[] NUMBERS = new String[] {
            "1", "2", "3", "4", "5"
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, NUMBERS);
        AutoCompleteTextView textView = binding.countInput;
        textView.setAdapter(adapter);

        Button addToCartButton = binding.addToCartButton;
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
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

        if (navigationOrigin == "search") {
            item = menu.findItem(R.id.navigation_search);
        } else {
            item = menu.findItem(R.id.navigation_home);
        }

        item.setChecked(true);
    }

    public void addToCart(){
        int count = Integer.parseInt(binding.countInput.getText().toString());

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

}