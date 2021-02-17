package com.allysonjeronimo.toshop.legacy.utils

import java.text.SimpleDateFormat
import java.util.*

const val DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss"

fun Date.fromFormattedString(dateStr:String, format:String = DEFAULT_DATE_PATTERN) : Date? {
    val formatter = SimpleDateFormat(format)
    return formatter.parse(dateStr)
}

fun Date.toFormattedString(format:String = DEFAULT_DATE_PATTERN) : String{
    val formatter = SimpleDateFormat(format)
    return formatter.format(this)
}