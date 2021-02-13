package com.allysonjeronimo.toshop.legacy.repositories

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.allysonjeronimo.toshop.R
import com.allysonjeronimo.toshop.legacy.entities.Item
import com.allysonjeronimo.toshop.legacy.util.ShoppingListSQLiteHelper
import com.allysonjeronimo.toshop.legacy.entities.Category
import com.allysonjeronimo.toshop.utils.*
import java.util.*
import kotlin.collections.ArrayList

class ItemRepositoryImpl(private val context: Context) : ItemRepository {

    private val helper = ShoppingListSQLiteHelper.getInstance(context)

    private fun create(item: Item){
        val db = helper.writableDatabase
        val values = ContentValues().apply {
            put(Item.COLUMN_DESCRIPTION, item.description)
            put(Item.COLUMN_QUANTITY, item.quantity)
            put(Item.COLUMN_UNIT, item.unit ?: context.resources.getStringArray(R.array.units)[0])
            put(Item.COLUMN_PRICE, item.price)
            put(Item.COLUMN_NOTES, item.notes)
            put(Item.COLUMN_LAST_UPDATE, Date().toFormattedString())
            put(Item.FK_CATEGORY_ID, if(item.categoryId != 0L) item.categoryId else 1)
            put(Item.FK_COLUMN_LIST_ID, item.listId)
        }
        val id = db.insert(Item.TABLE_NAME, null, values)
        if(id != 0L)
            item.id = id
        db.close()
    }

    private fun update(item: Item) {
        val db = helper.writableDatabase
        val values = ContentValues().apply {
            put(Item.COLUMN_DESCRIPTION, item.description)
            put(Item.COLUMN_QUANTITY, item.quantity)
            put(Item.COLUMN_UNIT, item.unit ?: context.resources.getStringArray(R.array.units)[0])
            put(Item.COLUMN_PRICE, item.price)
            put(Item.COLUMN_NOTES, item.notes)
            put(Item.COLUMN_PURCHASED, item.purchased.toInt())
            put(Item.COLUMN_LAST_UPDATE, Date().toFormattedString())
            put(Item.FK_CATEGORY_ID, if(item.categoryId != 0L) item.categoryId else 1)
            put(Item.FK_COLUMN_LIST_ID, item.listId)
        }
        db.update(
            Item.TABLE_NAME,
            values,
            "${Item.COLUMN_ID} = ?",
            arrayOf(item.id.toString()))
        db.close()
    }

    override fun save(item: Item){
        if(item.id == 0L)
            create(item)
        else
            update(item)
    }

    override fun delete(item: Item) {
        val db = helper.writableDatabase
        db.delete(
            Item.TABLE_NAME,
            "${Item.COLUMN_ID} = ?",
            arrayOf(item.id.toString()))
        db.close()
    }

    override fun deleteByShoppingList(shoppingListId: Long) {
        val db = helper.writableDatabase
        db.delete(
            Item.TABLE_NAME,
            "${Item.FK_COLUMN_LIST_ID} = ?",
            arrayOf(shoppingListId.toString()))
        db.close()
    }

    override fun updatePurchasedByShoppingList(purchased: Boolean, shoppingListId: Long) {
        val db = helper.writableDatabase
        val values = ContentValues().apply {
            put(Item.COLUMN_PURCHASED, purchased)
            put(Item.COLUMN_LAST_UPDATE, Date().toFormattedString())
        }
        db.update(
            Item.TABLE_NAME,
            values,
            "${Item.FK_COLUMN_LIST_ID} = ?",
            arrayOf(shoppingListId.toString()))
        db.close()
    }

    override fun find(id: Long): Item? {
        val sql = "SELECT * FROM ${Item.TABLE_NAME} WHERE ${Item.COLUMN_ID} = ?"
        val db = helper.readableDatabase
        val cursor = db.rawQuery(sql, arrayOf(id.toString()))
        val item = if(cursor.moveToNext()) fromCursor(cursor) else null
        cursor.close()
        db.close()
        return item
    }

    override fun findByShoppingList(shoppingListId:Long): List<Item> {
        val sql = "SELECT it.*, ct.${Category.COLUMN_RESOURCE_ICON_NAME} " +
                "FROM ${Item.TABLE_NAME} it, ${Category.TABLE_NAME} ct " +
                "WHERE it.${Item.FK_COLUMN_LIST_ID} = ? AND " +
                "it.${Item.FK_CATEGORY_ID} = ct.${Category.COLUMN_ID} "
                "ORDER BY it.${Item.COLUMN_ID} DESC"

        val db = helper.readableDatabase
        val cursor = db.rawQuery(sql, arrayOf(shoppingListId.toString()))
        var result = ArrayList<Item>()
        while(cursor.moveToNext()){
            result.add(fromCursor(cursor))
        }
        cursor.close()
        db.close()
        return result
    }

    override fun countByShoppingList(shoppingListId: Long) : Int{
        val sql = "SELECT Count(*) FROM ${Item.TABLE_NAME} WHERE ${Item.FK_COLUMN_LIST_ID} = ?"
        val db = helper.readableDatabase
        val cursor = db.rawQuery(sql, arrayOf(shoppingListId.toString()))
        var result:Int = 0
        if(cursor.moveToNext())
            result = cursor.getInt(0)
        cursor.close()
        db.close()
        return result
    }

    fun fromCursor(cursor: Cursor) : Item {
        val id = cursor.getLong(cursor.getColumnIndex(Item.COLUMN_ID))
        val description = cursor.getString(cursor.getColumnIndex(Item.COLUMN_DESCRIPTION))
        val quantity = cursor.getDouble(cursor.getColumnIndex(Item.COLUMN_QUANTITY))
        val unit = cursor.getString(cursor.getColumnIndex(Item.COLUMN_UNIT))
        val price = cursor.getDouble(cursor.getColumnIndex(Item.COLUMN_PRICE))
        val notes = cursor.getString(cursor.getColumnIndex(Item.COLUMN_NOTES))
        val purchased = cursor.getInt(cursor.getColumnIndex(Item.COLUMN_PURCHASED))
        val lastUpdate = cursor.getString(cursor.getColumnIndex(Item.COLUMN_LAST_UPDATE))
        val categoryId = cursor.getLong(cursor.getColumnIndex(Item.FK_CATEGORY_ID))
        val shoppingListId = cursor.getLong(cursor.getColumnIndex(Item.FK_COLUMN_LIST_ID))
        val resourceIcon = cursor.getString(cursor.getColumnIndex(Category.COLUMN_RESOURCE_ICON_NAME))

        val item = Item(
            id,
            description,
            quantity,
            unit,
            price,
            notes,
            purchased.toBoolean(),
            Date().fromFormattedString(lastUpdate) ?: Date(),
            categoryId,
            shoppingListId
        )

        item.category = Category(
            categoryId,
            resourceIcon)

        return item
    }
}