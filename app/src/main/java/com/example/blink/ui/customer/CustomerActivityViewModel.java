package com.example.blink.ui.customer;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class CustomerActivityViewModel extends ViewModel {
    public MutableLiveData<ArrayList<String>> selectedCategoryFilters = new MutableLiveData<>();
    public MutableLiveData<ArrayList<String>> selectedSupplierFilters = new MutableLiveData<>();
    public MutableLiveData<String> selectedSortOrder = new MutableLiveData<>();
    public MutableLiveData<Double> totalPriceOfCartItems = new MutableLiveData<>();
}
