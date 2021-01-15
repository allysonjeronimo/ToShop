package com.allysonjeronimo.toshop.model.db.repositories

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.allysonjeronimo.toshop.model.entities.ShoppingList
import com.allysonjeronimo.toshop.model.db.repositories.interfaces.ShoppingListRepository
import com.allysonjeronimo.toshop.model.db.util.ShoppingListSQLiteHelper
import com.allysonjeronimo.toshop.model.entities.Item
import com.allysonjeronimo.toshop.utils.fromFormattedString
import com.allysonjeronimo.toshop.utils.toFormattedString
import java.util.*
import kotlin.collections.ArrayList

class ShoppingListRepositoryImpl(context: Context) : ShoppingListRepository{

    private val helper = ShoppingListSQLiteHelper.getInstance(context)

    private fun create(shoppingList: ShoppingList){
        val db = helper.writableDatabase
        val values = ContentValues().apply{
            put(ShoppingList.COLUMN_DESCRIPTION, shoppingList.description)
            put(ShoppingList.COLUMN_LAST_UPDATE,  Date().toFormattedString())
        }
        var id = db.insert(ShoppingList.TABLE_NAME, null, values)

        if(id != 0L)
            shoppingList.id = id

        db.close()
    }

    private fun update(shoppingList: ShoppingList){
        val db = helper.writableDatabase
        val values = ContentValues().apply {
            put(ShoppingList.COLUMN_DESCRIPTION, shoppingList.description)
            put(ShoppingList.COLUMN_LAST_UPDATE, Date().toFormattedString())
        }
        db.update(
            ShoppingList.TABLE_NAME,
            values,
            "${ShoppingList.COLUMN_ID} = ?",
            arrayOf(shoppingList.id.toString()))
        db.close()
    }

    override fun save(shoppingList: ShoppingList){
        if(shoppingList.id == 0L)
            create(shoppingList)
        else
            update(shoppingList)
    }

    override fun delete(shoppingList: ShoppingList){
        val db = helper.writableDatabase
        db.delete(
            ShoppingList.TABLE_NAME,
            "${ShoppingList.COLUMN_ID} = ?",
            arrayOf(shoppingList.id.toString()))
        db.close()
    }

    override fun deleteAll() {
        val db = helper.writableDatabase
        db.delete(ShoppingList.TABLE_NAME, null, null)
        db.close()
    }

    override fun find(id:Long): ShoppingList?{
        val sql = "SELECT * FROM ${ShoppingList.TABLE_NAME} WHERE ${ShoppingList.COLUMN_ID} = ?"
        val db = helper.readableDatabase
        val cursor = db.rawQuery(sql, arrayOf(id.toString()))
        val shoppingList = if(cursor.moveToNext()) fromCursor(cursor, false) else null
        cursor.close()
        db.close()
        return shoppingList
    }

    override fun findAll(): kotlin.collections.List<ShoppingList> {
        val sql = "SELECT l.*, " +
                "(SELECT count(${Item.COLUMN_ID}) FROM ${Item.TABLE_NAME} WHERE ${Item.FK_COLUMN_LIST_ID} = l.${ShoppingList.COLUMN_ID}) as ${ShoppingList.TRANSIENT_QUANTITY_ITEMS}," +
                "(SELECT count(${Item.COLUMN_ID}) FROM ${Item.TABLE_NAME} WHERE ${Item.FK_COLUMN_LIST_ID} = l.${ShoppingList.COLUMN_ID} AND ${Item.COLUMN_PURCHASED} = 1) as ${ShoppingList.TRANSIENT_QUANTITY_PURCHASED_ITEMS}," +
                "(SELECT coalesce(sum(${Item.COLUMN_QUANTITY} * ${Item.COLUMN_PRICE}), 0.0) FROM ${Item.TABLE_NAME} WHERE ${Item.FK_COLUMN_LIST_ID} = l.${ShoppingList.COLUMN_ID} AND ${Item.COLUMN_PURCHASED} = 1) as ${ShoppingList.TRANSIENT_TOTAL} " +
                " FROM ${ShoppingList.TABLE_NAME} l ORDER BY l.${ShoppingList.COLUMN_ID} DESC"
        val db = helper.readableDatabase
        val cursor = db.rawQuery(sql, null)
        var result = ArrayList<ShoppingList>()
        while(cursor.moveToNext())
            result.add(fromCursor(cursor, true))
        cursor.close()
        db.close()
        return result
    }

    override fun count() : Int {
        val sql = "SELECT count(*) FROM ${ShoppingList.TABLE_NAME}"
        val db = helper.readableDatabase
        val cursor = db.rawQuery(sql, null)
        var result = 0
        if(cursor.moveToNext())
            result = cursor.getInt(0)
        cursor.close()
        db.close()
        return result
    }

    override fun search(term: String): kotlin.collections.List<ShoppingList> {

        val sql = "SELECT l.*, " +
                "(SELECT count(${Item.COLUMN_ID}) FROM ${Item.TABLE_NAME} WHERE ${Item.FK_COLUMN_LIST_ID} = l.${ShoppingList.COLUMN_ID}) as ${ShoppingList.TRANSIENT_QUANTITY_ITEMS}," +
                "(SELECT count(${Item.COLUMN_ID}) FROM ${Item.TABLE_NAME} WHERE ${Item.FK_COLUMN_LIST_ID} = l.${ShoppingList.COLUMN_ID} AND ${Item.COLUMN_PURCHASED} = 1) as ${ShoppingList.TRANSIENT_QUANTITY_PURCHASED_ITEMS}," +
                "(SELECT coalesce(sum(${Item.COLUMN_QUANTITY} * ${Item.COLUMN_PRICE}), 0.0) FROM ${Item.TABLE_NAME} WHERE ${Item.FK_COLUMN_LIST_ID} = l.${ShoppingList.COLUMN_ID} AND ${Item.COLUMN_PURCHASED} = 1) as ${ShoppingList.TRANSIENT_TOTAL} " +
                " FROM ${ShoppingList.TABLE_NAME} l " +
                " WHERE ${ShoppingList.COLUMN_DESCRIPTION} like ? " +
                " ORDER BY l.${ShoppingList.COLUMN_DESCRIPTION} ASC"

        val db = helper.readableDatabase
        val cursor = db.rawQuery(sql, arrayOf("%$term%"))
        val result = ArrayList<ShoppingList>()
        while(cursor.moveToNext())
            result.add(fromCursor(cursor, true))
        cursor.close()
        db.close()
        return result
    }

    private fun fromCursor(cursor: Cursor, transientColumns:Boolean = false): ShoppingList {
        val id = cursor.getLong(cursor.getColumnIndex(ShoppingList.COLUMN_ID))
        val description = cursor.getString(cursor.getColumnIndex(ShoppingList.COLUMN_DESCRIPTION))
        val lastUpdate = cursor.getString(cursor.getColumnIndex(ShoppingList.COLUMN_LAST_UPDATE))

        val list = ShoppingList(id, description, Date().fromFormattedString(lastUpdate) ?: Date())

        if(transientColumns){
            val quantityItems = cursor.getInt(cursor.getColumnIndex(ShoppingList.TRANSIENT_QUANTITY_ITEMS))
            val quantityPurchasedItems = cursor.getInt(cursor.getColumnIndex(ShoppingList.TRANSIENT_QUANTITY_PURCHASED_ITEMS))
            val total = cursor.getDouble(cursor.getColumnIndex(ShoppingList.TRANSIENT_TOTAL))

            list.quantityItems = quantityItems
            list.quantityPurchasedItems = quantityPurchasedItems
            list.total = total
        }

        return list
    }
}