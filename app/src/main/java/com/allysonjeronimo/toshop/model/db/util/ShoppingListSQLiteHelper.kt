package com.allysonjeronimo.toshop.model.db.util

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.allysonjeronimo.toshop.model.entities.*
import com.allysonjeronimo.toshop.model.entities.ShoppingList

class ShoppingListSQLiteHelper(private val context:Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object{
        private const val DB_NAME = "ToShop.db"
        private const val DB_VERSION = 1
        private var instance: ShoppingListSQLiteHelper? = null

        private const val LIST = "CREATE TABLE IF NOT EXISTS ${ShoppingList.TABLE_NAME} (" +
                "${ShoppingList.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${ShoppingList.COLUMN_DESCRIPTION} TEXT NOT NULL, " +
                "${ShoppingList.COLUMN_LAST_UPDATE} TEXT DEFAULT CURRENT_TIMESTAMP)"

        private const val ITEM = "CREATE TABLE IF NOT EXISTS ${Item.TABLE_NAME} (" +
                "${Item.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${Item.COLUMN_DESCRIPTION} TEXT NOT NULL, " +
                "${Item.COLUMN_QUANTITY} REAL DEFAULT 1, " +
                "${Item.COLUMN_UNIT} TEXT DEFAULT 'UN', " +
                "${Item.COLUMN_PRICE} REAL DEFAULT 0.0, " +
                "${Item.COLUMN_NOTES} TEXT, " +
                "${Item.COLUMN_PURCHASED} INTEGER DEFAULT 0, " +
                "${Item.COLUMN_LAST_UPDATE} TEXT DEFAULT CURRENT_TIMESTAMP, " +
                "${Item.FK_CATEGORY_ID} INTEGER DEFAULT 1, " +
                "${Item.FK_COLUMN_LIST_ID} INTEGER, " +
                "FOREIGN KEY (${Item.FK_COLUMN_LIST_ID}) " +
                "REFERENCES ${ShoppingList.TABLE_NAME} (${ShoppingList.COLUMN_ID}) " +
                "ON DELETE CASCADE)"

        private const val CATEGORY = "CREATE TABLE IF NOT EXISTS ${Category.TABLE_NAME} (" +
                "${Category.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${Category.COLUMN_RESOURCE_ICON_NAME} TEXT)"

        private const val CATEGORY_NAME = "CREATE TABLE IF NOT EXISTS ${CategoryName.TABLE_NAME} (" +
                "${CategoryName.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${CategoryName.COLUMN_LOCALE} TEXT, " +
                "${CategoryName.COLUMN_NAME} TEXT, " +
                "${CategoryName.FK_COLUMN_CATEGORY_ID} INTEGER REFERENCES ${Category.TABLE_NAME} (${Category.COLUMN_ID}))"

        private const val PRODUCT = "CREATE TABLE IF NOT EXISTS ${Product.TABLE_NAME} (" +
                "${Product.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${Product.FK_COLUMN_CATEGORY_ID} INTEGER REFERENCES ${Category.TABLE_NAME} (${Category.COLUMN_ID})) "

        private const val PRODUCT_NAME = "CREATE TABLE IF NOT EXISTS ${ProductName.TABLE_NAME} (" +
                "${ProductName.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${ProductName.COLUMN_LOCALE} TEXT, " +
                "${ProductName.COLUMN_NAME} TEXT, " +
                "${ProductName.FK_COLUMN_PRODUCT_ID} INTEGER REFERENCES ${Product.TABLE_NAME} (${Product.COLUMN_ID})) "

        private val SQL_CREATE = listOf<String>(
            LIST, ITEM, CATEGORY, CATEGORY_NAME, PRODUCT, PRODUCT_NAME
        )

        private const val INSERT_CATEGORY_TEMPLATE =
                "INSERT INTO ${Category.TABLE_NAME} (${Category.COLUMN_ID}, ${Category.COLUMN_RESOURCE_ICON_NAME}) VALUES (%d, '%s')"

        private const val INSERT_CATEGORY_NAME_TEMPLATE =
                "INSERT INTO ${CategoryName.TABLE_NAME} " +
                        "(${CategoryName.COLUMN_NAME},${CategoryName.COLUMN_LOCALE},${CategoryName.FK_COLUMN_CATEGORY_ID}) " +
                        "VALUES ('%s', '%s', %d)"

        private const val INSERT_PRODUCT_TEMPLATE =
            "INSERT INTO ${Product.TABLE_NAME} (${Product.COLUMN_ID}, ${Product.FK_COLUMN_CATEGORY_ID}) VALUES (%d, %d)"

        private const val INSERT_PRODUCT_NAME_TEMPLATE =
            "INSERT INTO ${ProductName.TABLE_NAME} (${ProductName.COLUMN_NAME},${ProductName.COLUMN_LOCALE},${ProductName.FK_COLUMN_PRODUCT_ID}) " +
                    "VALUES ('%s', '%s', %d)"

        fun getInstance(context:Context) : ShoppingListSQLiteHelper {
            if(instance == null)
                instance = ShoppingListSQLiteHelper(context)
            return instance!!
        }
    }

    override fun onOpen(db: SQLiteDatabase) {
        super.onOpen(db)
        db.execSQL("PRAGMA foreign_keys=ON")
    }

    override fun onCreate(db: SQLiteDatabase) {
        initDatabase(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

    private fun initDatabase(db:SQLiteDatabase){
        SQL_CREATE.forEach {
            db.execSQL(it)
        }
        insertCategories(db)
        insertProducts(db)
    }

    private fun insertCategories(db:SQLiteDatabase){
        val categories = DataHelper.getInstance(context).getCategories()
        categories.forEach { category ->

            db.execSQL(INSERT_CATEGORY_TEMPLATE.format(
                category.id, category.resourceIconName
            ))

            category.categoryNames.forEach { categoryName ->
                db.execSQL(INSERT_CATEGORY_NAME_TEMPLATE.format(
                    categoryName.name, categoryName.locale, categoryName.categoryId
                ))
            }
        }
    }

    private fun insertProducts(db:SQLiteDatabase){
        val products = DataHelper.getInstance(context).getProducts()
        products.forEach { product ->
            db.execSQL(INSERT_PRODUCT_TEMPLATE.format(
                product.id, product.categoryId
            ))
            product.productNames.forEach { productName ->
                db.execSQL(INSERT_PRODUCT_NAME_TEMPLATE.format(
                    productName.name, productName.locale, productName.productId
                ))
            }
        }
    }

}