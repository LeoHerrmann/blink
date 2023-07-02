package com.example.blink.ui.customer.search;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blink.R;

import java.util.List;
import java.util.Locale;

public class SearchAdapter extends RecyclerView.Adapter {
    Context context;
    List<SearchRecyclerViewItem> items;

    public SearchAdapter(Context context, List<SearchRecyclerViewItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchViewHolder(LayoutInflater.from(context).inflate(
                R.layout.sample_customer_search_product_view,
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SearchViewHolder searchViewHolder = (SearchViewHolder) holder;

        String productName = items.get(position).getProductName();
        String productPrice = String.format(Locale.getDefault(), "%.2f€", items.get(position).getProductPrice());
        String productSupplier = items.get(position).getProductSupplier();

        searchViewHolder.nameTextView.setText(productName);
        searchViewHolder.priceTextVew.setText(productPrice);
        searchViewHolder.supplierTextView.setText(productSupplier);

        searchViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchProductDetails(view, items.get(searchViewHolder.getAbsoluteAdapterPosition()).getProductId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void launchProductDetails(View view, int productId) {
        Bundle bundle = new Bundle();
        bundle.putInt("productId", productId);
        bundle.putString("navigationOrigin", "search");

        NavController navController = Navigation.findNavController((Activity) view.getContext(), R.id.nav_host_fragment_activity_customer_main);
        navController.navigate(R.id.action_navigation_search_to_productDetailsFragment, bundle);
    }
}
