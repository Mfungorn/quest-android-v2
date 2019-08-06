package com.example.app.utils

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visibleGone")
fun View.showHide(show: Boolean) {
    this.visibility = if (show) View.VISIBLE else View.GONE
}