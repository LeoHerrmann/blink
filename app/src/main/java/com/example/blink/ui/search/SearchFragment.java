package com.example.blink.ui.search;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.blink.R;
import com.example.blink.database.AppDatabase;
import com.example.blink.database.entities.Product;
import com.example.blink.databinding.FragmentSearchBinding;

import java.util.List;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initSearchBar();

        performSearch("");

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        SearchView searchView = getActivity().findViewById(R.id.searchView);
        String query = searchView.getQuery().toString();

        if (!query.isEmpty()) {
            performSearch(query);
            Log.d("", "query not empty -> search performed");
        }
        else {
            Log.d("", "query empty -> search not performed");
            searchView.setIconified(true);
        }
    }

    private void initSearchBar(){
        SearchView searchView = getActivity().findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Hier kannst du Aktionen ausführen, wenn sich der Text im Suchfeld ändert
                performSearch(newText);
                return false;
            }
        });
    }

    private void performSearch(String query) {
        AppDatabase db = AppDatabase.getInstance(requireContext().getApplicationContext());
        List<Product> products = db.productDao().GetWithNameLike(query);

        LinearLayout productContainer = binding.productContainer;
        productContainer.removeAllViews();

        for (Product product : products) {
            View productView = getLayoutInflater().inflate(R.layout.sample_search_product_view, null);
            TextView nameTextView = productView.findViewById(R.id.nameTextView);
            TextView priceTextView = productView.findViewById(R.id.priceTextView);
            TextView supplierTextView = productView.findViewById(R.id.supplierTextView);

            nameTextView.setText(product.name);
            priceTextView.setText(getPriceString(product.price));
            supplierTextView.setText(product.supplierName);

            productView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    launchProductDetails(v);
                }
            });

            productContainer.addView(productView);
        }
    }

    public void launchProductDetails(View v) {
        NavController navController = findNavController(v);

        TextView priceTextView = v.findViewById(R.id.priceTextView);
        TextView supplierTextView = v.findViewById(R.id.supplierTextView);
        TextView productTextView = v.findViewById(R.id.nameTextView);
        String price = (String) priceTextView.getText();
        String supplierName = (String) supplierTextView.getText();
        String productName = (String) productTextView.getText();

        Bundle bundle = new Bundle();
        bundle.putString("productName", productName);
        bundle.putString("supplierName", supplierName);
        bundle.putString("price", price);
        bundle.putString("navigationOrigin", "search");

        navController.navigate(R.id.action_navigation_search_to_productDetailsFragment, bundle);
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