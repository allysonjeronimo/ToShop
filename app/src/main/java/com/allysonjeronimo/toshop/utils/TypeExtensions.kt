package com.allysonjeronimo.toshop.utils

import android.content.Context
import com.allysonjeronimo.toshop.R
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

fun Boolean.toInt(): Int = if(this) 1 else 0

fun Int.toBoolean(): Boolean = this != 0

fun Double.toCurrency() : String {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
    return numberFormat.format(this)
}



