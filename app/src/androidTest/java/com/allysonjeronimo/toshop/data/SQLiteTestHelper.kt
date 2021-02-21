package com.allysonjeronimo.toshop.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.allysonjeronimo.toshop.legacy.util.SQLiteDataHelper

class SQLiteTestHelper(
    private val context: Context,
    private val name:String
    ) : SQLiteOpenHelper(context, name, null, 1){

    override fun onCreate(db: SQLiteDatabase?) {
        SQLiteDataHelper.getInstance(context).initDatabase(db as SQLiteDatabase)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}