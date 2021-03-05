package com.allysonjeronimo.toshop.extensions

import android.content.Context
import android.content.SharedPreferences
import com.allysonjeronimo.toshop.R

class PrefsHelper(
    context: Context){

    private var sharedPreferences:SharedPreferences? = null

    init{
        sharedPreferences = context
            .getSharedPreferences(
                context.getString(R.string.preferences_key),
                Context.MODE_PRIVATE)
    }

    companion object{
        private var instance: PrefsHelper? = null

        const val KEY_MOVE_ITEMS = "move_items"
        const val KEY_SHOW_SUMMARY = "show_summary"
        const val KEY_SCREEN_AWAKE = "screen_awake"

        fun getInstance(context:Context) : PrefsHelper {
            if(instance == null)
                instance = PrefsHelper(context)
            return instance!!
        }
    }

    fun getBooleanValue(key:String) : Boolean{
        return sharedPreferences?.getBoolean(key, true) ?: false
    }
}