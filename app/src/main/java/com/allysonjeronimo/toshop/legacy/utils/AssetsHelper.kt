package com.allysonjeronimo.toshop.legacy.utils

import android.content.Context

class AssetsHelper(private val context: Context){

    companion object{

        private var instance:AssetsHelper? = null

        const val FILE_CATEGORIES = "categories.json"
        const val FILE_PRODUCTS = "products.json"

        fun getInstance(context:Context) : AssetsHelper{
            if(instance == null)
                instance = AssetsHelper(context)
            return instance!!
        }
    }

    fun loadJSON(fileName:String) : String{
        return context.assets.open(fileName).bufferedReader().use{it.readText()}
    }
}