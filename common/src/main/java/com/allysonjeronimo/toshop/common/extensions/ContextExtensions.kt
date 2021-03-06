package com.allysonjeronimo.toshop.common.extensions

import android.content.Context

fun Context.loadFileFromAssets(fileName:String) : String{
    return this.assets.open(fileName).bufferedReader().use{it.readText()}
}