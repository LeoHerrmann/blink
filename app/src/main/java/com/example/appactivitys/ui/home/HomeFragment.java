package com.example.appactivitys.ui.home;

import static androidx.navigation.Navigation.findNavController;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.appactivitys.R;
import com.example.appactivitys.databinding.FragmentHomeBinding;
import com.example.appactivitys.ui.ProductDetailsFragment;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        CardView cardView = root.findViewById(R.id.productImageView);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchProductDetails(v);
            }
        });
        int displayWidth = getDisplayWidth(requireContext());


        View item = root.findViewById(R.id.gridItem);
        int itemWidth = getItemWidthInPixels(item);
        int columnCount = displayWidth / itemWidth;
        int magicOne = 1; //Fuer die ganzen Paddings

        GridLayout gridLayout = root.findViewById(R.id.categoriesGridLayout);
        gridLayout.setColumnCount(columnCount - magicOne);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void launchProductDetails(View v) {
        NavController navController = findNavController(v);
        navController.navigate(R.id.action_navigation_home_to_productDetailsFragment);
    }

    private int getDisplayWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    private int getItemWidthInPixels(View item) {
        int itemWidth = item.getWidth();
        if (itemWidth == 0) {
            // The item's width is not yet calculated, measure it manually
            item.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            itemWidth = item.getMeasuredWidth();
        }
        return itemWidth;
    }

}