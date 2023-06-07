package com.example.appactivitys;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.appactivitys.database.AppDatabase;
import com.example.appactivitys.database.Product;
import com.example.appactivitys.databinding.FragmentHomeBinding;
import com.example.appactivitys.databinding.FragmentSearchBinding;

import org.w3c.dom.Text;

import java.util.List;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initSearchBar();

        return root;
    }

    private void initSearchBar(){
        androidx.appcompat.widget.SearchView searchView = getActivity().findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Hier kannst du Aktionen ausführen, wenn sich der Text im Suchfeld ändert
                return false;
            }
        });
    }

    private void performSearch(String query) {
        Log.d("suche", query);

        AppDatabase db = AppDatabase.getInstance(requireContext().getApplicationContext());
        List<Product> products = db.productDao().GetWithNameLike(query);

        for (Product product : products) {
            View productView = getLayoutInflater().inflate(R.layout.sample_search_product_view, null);
            TextView nameTextView = productView.findViewById(R.id.nameTextView);
            TextView priceTextView = productView.findViewById(R.id.priceTextView);
            TextView supplierTextView = productView.findViewById(R.id.supplierTextView);

            nameTextView.setText(product.name);
            priceTextView.setText(getPriceString(product.price));
            supplierTextView.setText(product.supplierName);

            LinearLayout productContainer = binding.productContainer;
            productContainer.addView(productView);
        }
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