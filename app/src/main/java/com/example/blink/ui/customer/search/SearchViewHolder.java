package com.example.blink.ui.customer.search;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blink.R;

public class SearchViewHolder extends RecyclerView.ViewHolder {
    TextView nameTextView;
    TextView priceTextVew;
    TextView supplierTextView;

    public SearchViewHolder(@NonNull View itemView) {
        super(itemView);

        nameTextView = itemView.findViewById(R.id.nameTextView);
        priceTextVew = itemView.findViewById(R.id.priceTextView);
        supplierTextView = itemView.findViewById(R.id.supplierTextView);
    }
}
