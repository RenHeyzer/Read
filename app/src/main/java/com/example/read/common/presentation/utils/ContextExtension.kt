package com.example.read.common.presentation.utils

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

val Fragment.notNullContext
    get() = context ?: requireContext()

fun Context.getAttributeColor(attr: Int): Int {
    val typedValue = android.util.TypedValue()
    this.theme.resolveAttribute(attr, typedValue, true)
    return ContextCompat.getColor(this, typedValue.resourceId)
}