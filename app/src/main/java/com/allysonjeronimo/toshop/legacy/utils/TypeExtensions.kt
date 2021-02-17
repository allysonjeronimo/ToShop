package com.allysonjeronimo.toshop.legacy.utils

import java.text.NumberFormat
import java.util.*

fun Boolean.toInt(): Int = if(this) 1 else 0

fun Int.toBoolean(): Boolean = this != 0

fun Double.toCurrency() : String {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
    return numberFormat.format(this)
}



