package com.example.blink.ui.search;

import static androidx.navigation.Navigation.findNavController;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.blink.CustomerMainViewModel;
import com.example.blink.R;
import com.example.blink.database.AppDatabase;
import com.example.blink.database.entities.Category;
import com.example.blink.database.entities.Product;
import com.example.blink.database.entities.Supplier;
import com.example.blink.databinding.FragmentSearchBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;

    private CustomerMainViewModel customerMainViewModel;

    private List<Category> categories;
    private List<Supplier> suppliers;

    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        AppDatabase db = AppDatabase.getInstance(getActivity().getApplicationContext());
        categories = db.categoryDao().GetAll();
        suppliers = db.supplierDao().GetAll();

        customerMainViewModel = new ViewModelProvider(getActivity()).get(CustomerMainViewModel.class);

        initSearchBar();

        performSearch();

        setupOrderDialog();
        setupCategoryFilterDialog();
        setupSupplierFilterDialog();
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

    private void setupCategoryFilterDialog() {
        Chip categoryFilterButton = binding.categoryFilterChip;

        categoryFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCategoryFilterView();
            }}
        );
    }

    private void setupSupplierFilterDialog() {
        Chip supplierFilterButton = binding.supplierFilterChip;

        supplierFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSupplierFilterView();
            }}
        );
    }

    private void setupOrderDialog() {
        Chip orderButton = binding.orderChip;

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOrderView();
            }
        });
    }

    private void showCategoryFilterView() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.sample_category_filter_view);

        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                //TODO: auslagern :D
                //herausfinden, welche Checkboxen ausgewählt sind
                LinearLayout checkboxContainer = bottomSheetDialog.findViewById(R.id.checkboxContainer);
                int childElementCount = checkboxContainer.getChildCount();

                ArrayList<String> selectedCategories = new ArrayList<>();

                for (int i = 0; i < childElementCount; i++) {
                    View childElement = checkboxContainer.getChildAt(i);

                    if (childElement instanceof CheckBox) {
                        CheckBox childAsCheckbox = (CheckBox) childElement;
                        if (childAsCheckbox.isChecked()) {
                            String checkBoxText = childAsCheckbox.getText().toString();
                            selectedCategories.add(checkBoxText);
                        }
                    }
                }

                //ausgewähöte Kategorien abspeichern
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
        bottomSheetDialog.setContentView(R.layout.sample_supplier_filter_view);

        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                //TODO: auslagern :D
                //herausfinden, welche Checkboxen ausgewählt sind
                LinearLayout checkboxContainer = bottomSheetDialog.findViewById(R.id.checkboxContainer);
                int childElementCount = checkboxContainer.getChildCount();

                ArrayList<String> selectedSuppliers = new ArrayList<>();

                for (int i = 0; i < childElementCount; i++) {
                    View childElement = checkboxContainer.getChildAt(i);

                    if (childElement instanceof CheckBox) {
                        CheckBox childAsCheckbox = (CheckBox) childElement;
                        if (childAsCheckbox.isChecked()) {
                            String checkBoxText = childAsCheckbox.getText().toString();
                            selectedSuppliers.add(checkBoxText);
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
        bottomSheetDialog.setContentView(R.layout.sample_product_order_view);

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

        if (selectedOrder == "NameZA") {
            radiotoSelect = bottomSheetDialog.findViewById(R.id.radioNameZA);
        }
        else if (selectedOrder == "Price09") {
            radiotoSelect = bottomSheetDialog.findViewById(R.id.radioPrice09);
        }
        else if (selectedOrder == "Price90") {
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

        AppDatabase db = AppDatabase.getInstance(requireContext().getApplicationContext());
        List<Product> products = db.productDao().GetWithNameLike(query, customerMainViewModel.selectedCategoryFilters.getValue(), customerMainViewModel.selectedSupplierFilters.getValue());

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

    private void launchProductDetails(View v) {
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