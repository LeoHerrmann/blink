package com.example.blink;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ProviderMainViewModel extends ViewModel {
    public MutableLiveData<String> providerName = new MutableLiveData<>();
}
