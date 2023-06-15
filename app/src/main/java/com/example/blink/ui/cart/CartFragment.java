package com.example.blink.ui.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.blink.R;
import com.example.blink.database.AppDatabase;
import com.example.blink.database.entities.CartItem;
import com.example.blink.database.entities.Product;
import com.example.blink.databinding.FragmentCartBinding;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    private FragmentCartBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        AppDatabase db = AppDatabase.getInstance(getActivity().getApplicationContext());
        List<CartItem> cartItems = db.cartItemDao().GetAll();
        ArrayList<Integer> productIds = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            productIds.add(cartItem.productId);
        }

        List<Product> products = db.productDao().GetProductsWithIds(productIds);

        for (CartItem cartItem : cartItems) {
            String name = "";
            String price = "";
            String supplier = "";

            for (Product product : products) {
                if (product.productId == cartItem.productId) {
                    name = product.name;
                    price = getPriceString(product.price);
                    supplier = product.supplierName;
                }
            }

            View cartItemView = getLayoutInflater().inflate(R.layout.sample_cart_item_view, null);
            TextView nameTextView = cartItemView.findViewById(R.id.productName);
            TextView priceTextView = cartItemView.findViewById(R.id.productPrice);
            TextView supplierTextView = cartItemView.findViewById(R.id.productSupplier);
            AutoCompleteTextView countInput = cartItemView.findViewById(R.id.countInput);

            nameTextView.setText(name);
            priceTextView.setText(price);
            supplierTextView.setText(supplier);
            countInput.setText(cartItem.count.toString());

            ArrayList<String> NUMBERS = new ArrayList<>();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, NUMBERS);
            AutoCompleteTextView textView = countInput;
            textView.setAdapter(adapter);

            for(Integer i = 1; i < cartItem.count*1.3 || i < 5; i++){
                NUMBERS.add(i.toString());
            }

            binding.mainContent.addView(cartItemView);
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private String getPriceString(double price) {
        String priceString = String.valueOf(price);

        if (priceString.contains(".")) {
            // Wir trennen den String in zwei Teile: den Teil vor dem Punkt und den Teil danach
            String[] teile = priceString.split("\\.");

            // Überprüfen, ob der Teil nach dem Punkt weniger als zwei Stellen hat
            if (teile[1].length() < 2) {
                // Füge Nullen hinzu, um auf zwei Nachkommastellen zu kommen
                teile[1] = teile[1] + "0";
            }

            // Verbinde die Teile wieder zu einem String
            priceString = teile[0] + "." + teile[1];
        } else {
            // Wenn der String keinen Punkt enthält, fügen wir ".00" hinzu
            priceString = priceString + ".00";
        }

        priceString += "€";

        return priceString;
    }
}