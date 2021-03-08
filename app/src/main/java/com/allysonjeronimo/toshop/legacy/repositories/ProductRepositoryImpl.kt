package com.allysonjeronimo.toshop.legacy.repositories

import android.content.Context
import android.database.Cursor
import com.allysonjeronimo.toshop.legacy.util.ShoppingListSQLiteHelper
import com.allysonjeronimo.toshop.legacy.entities.Category
import com.allysonjeronimo.toshop.legacy.entities.Product
import com.allysonjeronimo.toshop.legacy.entities.ProductName

class ProductRepositoryImpl(private val context:Context) : ProductRepository {

    private val helper = ShoppingListSQLiteHelper.getInstance(context)

    override fun search(term: String): List<Product> {
        val db = helper.readableDatabase
        val sql = "SELECT p.${Product.COLUMN_ID}, p.${Product.FK_COLUMN_CATEGORY_ID}, " +
                "pn.${ProductName.COLUMN_NAME}, ct.${Category.COLUMN_RESOURCE_ICON_NAME} " +
                    "FROM ${Product.TABLE_NAME} p, ${ProductName.TABLE_NAME} pn, ${Category.TABLE_NAME} ct " +
                    "WHERE p.${Product.COLUMN_ID} = pn.${ProductName.FK_COLUMN_PRODUCT_ID} AND " +
                    "pn.${ProductName.COLUMN_NAME} like ? AND " +
                    "pn.${ProductName.COLUMN_LOCALE} = ? AND " +
                     "p.${Product.FK_COLUMN_CATEGORY_ID} = ct.${Category.COLUMN_ID} "
                    "ORDER BY pn.${ProductName.COLUMN_NAME} ASC"
        val cursor = db.rawQuery(
            sql,
            arrayOf("$term%", "pt-BR")
            )
        val result = ArrayList<Product>()
        while(cursor.moveToNext()){
            result.add(fromCursor(cursor))
        }
        cursor.close()
        db.close()
        return result
    }

    private fun fromCursor(cursor: Cursor) : Product {
        val id = cursor.getLong(cursor.getColumnIndex(Product.COLUMN_ID))
        val categoryId = cursor.getLong(cursor.getColumnIndex(Product.FK_COLUMN_CATEGORY_ID))
        val name = cursor.getString(cursor.getColumnIndex(Product.TRANSIENT_NAME))
        val resourceIcon = cursor.getString(cursor.getColumnIndex(Category.COLUMN_RESOURCE_ICON_NAME))

        val product = Product(
            id,
            categoryId,
            name
        )

        product.category = Category(categoryId, resourceIcon)

        return product
    }
}