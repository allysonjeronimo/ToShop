package com.allysonjeronimo.toshop.common.extensions

import android.content.Context
import java.util.*

fun Context.loadFileFromAssets(fileName:String) : String{
    return this.assets.open(fileName).bufferedReader().use{it.readText()}
}

fun Context.locale() : String {
    return "${Locale.getDefault().language}-${Locale.getDefault().country}"
}

