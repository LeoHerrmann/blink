package com.example.blink.ui.customer;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.blink.R;
import com.example.blink.database.AppDatabase;
import com.example.blink.database.entities.Category;
import com.example.blink.database.entities.Product;
import com.example.blink.database.entities.Supplier;
import com.example.blink.databinding.FragmentCustomerSearchBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchFragment extends Fragment {

    private FragmentCustomerSearchBinding binding;

    private CustomerActivityViewModel customerMainViewModel;

    private List<Category> categories;
    private List<Supplier> suppliers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCustomerSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        AppDatabase db = AppDatabase.getInstance(getActivity().getApplicationContext());
        categories = db.categoryDao().GetAll();
        suppliers = db.supplierDao().GetAll();

        customerMainViewModel = new ViewModelProvider(getActivity()).get(CustomerActivityViewModel.class);

        initSearchBar();
        setupDialogs();

        performSearch();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        SearchView searchView = getActivity().findViewById(R.id.searchView);
        String query = searchView.getQuery().toString();

        if (!query.isEmpty()) {
            performSearch();
            Log.d("", "query not empty -> search performed");
        }
        else {
            Log.d("", "query empty -> search not performed");
            searchView.setIconified(true);
        }
    }

    private void setupDialogs() {
        Chip categoryFilterButton = binding.categoryFilterChip;
        Chip supplierFilterButton = binding.supplierFilterChip;
        Chip orderButton = binding.orderChip;

        if (customerMainViewModel.selectedCategoryFilters.getValue().size() < categories.size()) {
            binding.categoryFilterChip.setChecked(true);
        }

        if (customerMainViewModel.selectedSupplierFilters.getValue().size() < suppliers.size()) {
            binding.supplierFilterChip.setChecked(true);
        }

        if (customerMainViewModel.selectedSortOrder.getValue().equals("NameZA")) {
            orderButton.setText(R.string.SortingZA);
        }
        else if (customerMainViewModel.selectedSortOrder.getValue().equals("Price09")) {
            orderButton.setText(R.string.Sorting09);
        }
        else if (customerMainViewModel.selectedSortOrder.getValue().equals("Price90")) {
            orderButton.setText(R.string.Sorting90);
        }
        else {
            orderButton.setText(R.string.SortingAZ);
        }

        categoryFilterButton.setOnClickListener(v -> showCategoryFilterView());
        supplierFilterButton.setOnClickListener(v -> showSupplierFilterView());
        orderButton.setOnClickListener(v -> showOrderView());
    }

    private void showCategoryFilterView() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.sample_customer_search_filter_category_view);

        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                //TODO: auslagern :D
                //herausfinden, welche Checkboxen ausgewählt sind
                LinearLayout checkboxContainer = bottomSheetDialog.findViewById(R.id.checkboxContainer);
                int childElementCount = checkboxContainer.getChildCount();

                ArrayList<String> selectedCategories = new ArrayList<>();

                binding.categoryFilterChip.setChecked(false);

                for (int i = 0; i < childElementCount; i++) {
                    View childElement = checkboxContainer.getChildAt(i);

                    if (childElement instanceof CheckBox) {
                        CheckBox childAsCheckbox = (CheckBox) childElement;
                        if (childAsCheckbox.isChecked()) {
                            String checkBoxText = childAsCheckbox.getText().toString();
                            selectedCategories.add(checkBoxText);
                        }
                        else {
                            binding.categoryFilterChip.setChecked(true);
                        }
                    }
                }

                //ausgewählte Kategorien abspeichern
                customerMainViewModel.selectedCategoryFilters.setValue(selectedCategories);

                //Suche ausführen
                performSearch();
            }
        });

        //Für jede Kategorie Checkbox hinzufügen
        for (Category category : categories) {
            Context context = getActivity();
            CheckBox checkBox = new CheckBox(context);
            checkBox.setText(category.name);

            if (customerMainViewModel.selectedCategoryFilters.getValue().contains(category.name)) {
                checkBox.setChecked(true);
            }

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    dpToPx(56)
            );

            LinearLayout checkboxContainer = bottomSheetDialog.findViewById(R.id.checkboxContainer);
            checkboxContainer.addView(checkBox, layoutParams);
        }

        bottomSheetDialog.show();
    }

    private void showSupplierFilterView() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.sample_customer_search_filter_supplier_view);

        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                //TODO: auslagern :D
                //herausfinden, welche Checkboxen ausgewählt sind
                LinearLayout checkboxContainer = bottomSheetDialog.findViewById(R.id.checkboxContainer);
                int childElementCount = checkboxContainer.getChildCount();

                ArrayList<String> selectedSuppliers = new ArrayList<>();

                binding.supplierFilterChip.setChecked(false);

                for (int i = 0; i < childElementCount; i++) {
                    View childElement = checkboxContainer.getChildAt(i);

                    if (childElement instanceof CheckBox) {
                        CheckBox childAsCheckbox = (CheckBox) childElement;
                        if (childAsCheckbox.isChecked()) {
                            String checkBoxText = childAsCheckbox.getText().toString();
                            selectedSuppliers.add(checkBoxText);
                        }
                        else {
                            binding.supplierFilterChip.setChecked(true);
                        }
                    }
                }

                //ausgewähöte Kategorien abspeichern
                customerMainViewModel.selectedSupplierFilters.setValue(selectedSuppliers);

                //Suche ausführen
                performSearch();
            }
        });

        //Für jede Kategorie Checkbox hinzufügen
        for (Supplier supplier : suppliers) {
            Context context = getActivity();
            CheckBox checkBox = new CheckBox(context);
            checkBox.setText(supplier.name);

            if (customerMainViewModel.selectedSupplierFilters.getValue().contains(supplier.name)) {
                checkBox.setChecked(true);
            }

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    dpToPx(56)
            );

            LinearLayout checkboxContainer = bottomSheetDialog.findViewById(R.id.checkboxContainer);
            checkboxContainer.addView(checkBox, layoutParams);
        }

        bottomSheetDialog.show();
    }

    private void showOrderView() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.sample_customer_search_order_view);

        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                //TODO: auslagern :D
                //herausfinden, welcher Radiobutton ausgewählt ist
                RadioGroup checkboxContainer = bottomSheetDialog.findViewById(R.id.checkboxContainer);
                int childElementCount = checkboxContainer.getChildCount();

                String selectedOrder = "NameAZ";

                for (int i = 0; i < childElementCount; i++) {
                    View childElement = checkboxContainer.getChildAt(i);

                    if (childElement instanceof RadioButton) {
                        RadioButton childAsRadioButton = (RadioButton) childElement;
                        if (childAsRadioButton.isChecked()) {
                            if (childAsRadioButton.getId() == R.id.radioNameAZ) {
                                selectedOrder = "NameAZ";
                                binding.orderChip.setText(R.string.SortingAZ);
                            }
                            else if (childAsRadioButton.getId() == R.id.radioNameZA) {
                                selectedOrder = "NameZA";
                                binding.orderChip.setText(R.string.SortingZA);
                            }
                            else if (childAsRadioButton.getId() == R.id.radioPrice09) {
                                selectedOrder = "Price09";
                                binding.orderChip.setText(R.string.Sorting09);
                            }
                            else if (childAsRadioButton.getId() == R.id.radioPrice90) {
                                selectedOrder = "Price90";
                                binding.orderChip.setText(R.string.Sorting90);
                            }
                        }
                    }
                }

                //ausgewähöte Kategorien abspeichern
                customerMainViewModel.selectedSortOrder.setValue(selectedOrder);

                //Suche ausführen
                performSearch();
            }
        });

        //Richtigen Radio-Button auswählen

        String selectedOrder = customerMainViewModel.selectedSortOrder.getValue();
        RadioButton radiotoSelect;

        if (selectedOrder.equals("NameZA")) {
            radiotoSelect = bottomSheetDialog.findViewById(R.id.radioNameZA);
        }
        else if (selectedOrder.equals("Price09")) {
            radiotoSelect = bottomSheetDialog.findViewById(R.id.radioPrice09);
        }
        else if (selectedOrder.equals("Price90")) {
            radiotoSelect = bottomSheetDialog.findViewById(R.id.radioPrice90);
        }
        else {
            radiotoSelect = bottomSheetDialog.findViewById(R.id.radioNameAZ);
        }

        radiotoSelect.setChecked(true);

        bottomSheetDialog.show();
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    private void initSearchBar(){
        SearchView searchView = getActivity().findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch();
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                performSearch();
                return false;
            }
        });
    }

    private void performSearch() {
        SearchView searchView = getActivity().findViewById(R.id.searchView);
        String query = searchView.getQuery().toString();

        String order = customerMainViewModel.selectedSortOrder.getValue();

        if (order == null) {
            order = "NameAZ";
        }

        AppDatabase db = AppDatabase.getInstance(requireContext().getApplicationContext());

        List<Product> products = db.productDao().GetWithNameLike(
                query,
                order,
                customerMainViewModel.selectedCategoryFilters.getValue(),
                customerMainViewModel.selectedSupplierFilters.getValue()
        );

        LinearLayout productContainer = binding.productContainer;
        productContainer.removeAllViews();

        for (Product product : products) {
            View productView = getLayoutInflater().inflate(R.layout.sample_customer_search_product_view, null);
            TextView nameTextView = productView.findViewById(R.id.nameTextView);
            TextView priceTextView = productView.findViewById(R.id.priceTextView);
            TextView supplierTextView = productView.findViewById(R.id.supplierTextView);

            nameTextView.setText(product.name);
            priceTextView.setText(String.format(Locale.getDefault(), "%.2f€", product.price));
            supplierTextView.setText(product.supplierName);

            productView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    launchProductDetails(product.productId);
                }
            });

            productContainer.addView(productView);
        }
    }

    private void launchProductDetails(int productId) {
        Bundle bundle = new Bundle();
        bundle.putInt("productId", productId);
        bundle.putString("navigationOrigin", "search");

        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_customer_main);
        navController.navigate(R.id.action_navigation_search_to_productDetailsFragment, bundle);
    }
}