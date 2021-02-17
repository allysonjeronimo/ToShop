package com.allysonjeronimo.toshop.legacy.repositories

import android.content.Context
import android.database.Cursor
import com.allysonjeronimo.toshop.legacy.util.ShoppingListSQLiteHelper
import com.allysonjeronimo.toshop.legacy.entities.Category
import com.allysonjeronimo.toshop.legacy.entities.CategoryName
import com.allysonjeronimo.toshop.legacy.utils.locale
import kotlin.collections.ArrayList

class CategoryRepositoryImpl(private val context: Context) : CategoryRepository {

    private val helper = ShoppingListSQLiteHelper.getInstance(context)

    override fun findAll(): List<Category> {
        val db = helper.readableDatabase
        val sql = "SELECT ct.${Category.COLUMN_ID}, ct.${Category.COLUMN_RESOURCE_ICON_NAME}, cn.${CategoryName.COLUMN_NAME} " +
                "FROM ${Category.TABLE_NAME} ct, ${CategoryName.TABLE_NAME} cn " +
                "WHERE ct.${Category.COLUMN_ID} = cn.${CategoryName.FK_COLUMN_CATEGORY_ID} " +
                "AND cn.${CategoryName.COLUMN_LOCALE} = ? ORDER BY ct.${Category.COLUMN_ID} ASC"
        val cursor = db.rawQuery(
            sql,
            arrayOf(context.locale()))
        val result = ArrayList<Category>()
        while(cursor.moveToNext()){
            result.add(fromCursor(cursor))
        }
        cursor.close()
        db.close()
        return result
    }

    private fun fromCursor(cursor: Cursor): Category {
        val id = cursor.getLong(cursor.getColumnIndex(Category.COLUMN_ID))
        val resourceIconName = cursor.getString(cursor.getColumnIndex(Category.COLUMN_RESOURCE_ICON_NAME))
        val name = cursor.getString(cursor.getColumnIndex(Category.TRANSIENT_NAME))

        return Category(
            id, resourceIconName, name
        )
    }

}