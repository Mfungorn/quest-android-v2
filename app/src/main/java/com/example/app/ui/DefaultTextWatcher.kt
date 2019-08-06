package com.example.app.ui

import android.text.Editable
import android.text.TextWatcher

abstract class DefaultTextWatcher : TextWatcher {
    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

    }

    abstract override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int)

    override fun afterTextChanged(s: Editable) {

    }
}
