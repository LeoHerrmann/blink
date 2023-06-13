package com.example.blink.ui.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class CustomerMainViewModel extends ViewModel {
    public MutableLiveData<ArrayList<String>> selectedCategoryFilters = new MutableLiveData<ArrayList<String>>();
    public MutableLiveData<ArrayList<String>> selectedSupplierFilters = new MutableLiveData<ArrayList<String>>();
}
