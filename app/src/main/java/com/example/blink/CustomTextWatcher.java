package com.example.blink;

import android.text.TextWatcher;
import android.widget.EditText;

public abstract class CustomTextWatcher implements TextWatcher {
    private final EditText editText;

    public CustomTextWatcher(EditText editText) {
        this.editText = editText;
    }

    public EditText getEditTextView() {
        return editText;
    }
}
