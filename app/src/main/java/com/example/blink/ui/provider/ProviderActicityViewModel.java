package com.example.blink.ui.provider;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProviderActicityViewModel extends ViewModel {
    public MutableLiveData<String> providerName = new MutableLiveData<>();
}
