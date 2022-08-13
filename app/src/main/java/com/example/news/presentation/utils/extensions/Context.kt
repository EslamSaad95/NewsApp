package com.example.news.presentation.utils.extensions

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Context.hideKeyboard(view: View?) = view?.let {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (imm.isActive) {
        imm.hideSoftInputFromWindow(it.windowToken, 0)
    }
}