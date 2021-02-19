package com.allysonjeronimo.toshop.legacy.util

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.allysonjeronimo.toshop.legacy.entities.*
import com.allysonjeronimo.toshop.legacy.entities.ShoppingList

class ShoppingListSQLiteHelper(
    private val context:Context
) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object{
        private const val DB_NAME = "ToShop.db"
        private const val DB_VERSION = 1
        private var instance: ShoppingListSQLiteHelper? = null

        fun getInstance(context:Context) : ShoppingListSQLiteHelper {
            if(instance == null)
                instance = ShoppingListSQLiteHelper(context)
            return instance!!
        }
    }

    fun clear(db:SQLiteDatabase){
        DataHelper.getInstance(context).clearDatabase(db)
    }

    override fun onOpen(db: SQLiteDatabase) {
        super.onOpen(db)
        db.execSQL("PRAGMA foreign_keys=ON")
    }

    override fun onCreate(db: SQLiteDatabase) {
        DataHelper.getInstance(context).initDatabase(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

}