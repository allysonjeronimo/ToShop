package com.allysonjeronimo.toshop.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class PrefsHelper(
    private val context: Context){

    private var sharedPreferences:SharedPreferences? = null

    init{
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    }

    companion object{
        private var instance:PrefsHelper? = null

        const val KEY_MOVE_ITENS = "move_items"
        const val KEY_SHOW_SUMMARY = "show_summary"
        const val KEY_SCREEN_AWAKE = "screen_awake"

        fun getInstance(context:Context) : PrefsHelper{
            if(instance == null)
                instance = PrefsHelper(context)
            return instance!!
        }
    }

    fun getBooleanValue(key:String) : Boolean{
        return sharedPreferences?.getBoolean(key, true) ?: false
    }
}