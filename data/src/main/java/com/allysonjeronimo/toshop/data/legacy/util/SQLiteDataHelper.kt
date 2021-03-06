package com.allysonjeronimo.toshop.legacy.util

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.allysonjeronimo.toshop.data.legacy.entities.*
import com.allysonjeronimo.toshop.legacy.entities.*
import com.allysonjeronimo.toshop.legacy.utils.AssetsHelper
import org.json.JSONArray

class SQLiteDataHelper(private val context:Context){

    companion object{

        private var instance: SQLiteDataHelper? = null

        fun getInstance(context: Context) : SQLiteDataHelper {
            if(instance == null)
                instance = SQLiteDataHelper(context)
            return instance!!
        }

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

        val SQL_CREATE = listOf<String>(
            LIST, ITEM, CATEGORY, CATEGORY_NAME, PRODUCT, PRODUCT_NAME
        )

        const val INSERT_CATEGORY_TEMPLATE =
            "INSERT INTO Category (id, resource_icon_name) VALUES (%d, '%s')"

        const val INSERT_CATEGORY_NAME_TEMPLATE =
            "INSERT INTO CategoryName " +
                    "(name, locale, category_id) " +
                    "VALUES ('%s', '%s', %d)"

        const val INSERT_PRODUCT_TEMPLATE =
            "INSERT INTO Product (id, category_id) VALUES (%d, %d)"

        const val INSERT_PRODUCT_NAME_TEMPLATE =
            "INSERT INTO ProductName (name, locale, product_id) " +
                    "VALUES ('%s', '%s', %d)"

        const val DELETE_TEMPLATE =
            "DELETE FROM %s"
    }

    private fun getCategories() : List<Category>{
        val categoriesJson = AssetsHelper.getInstance(context).loadJSON(AssetsHelper.FILE_CATEGORIES)
        val categoriesArray = JSONArray(categoriesJson)
        val categories = mutableListOf<Category>()
        for(i in 0 until categoriesArray.length()){
            val categoryObject = categoriesArray.getJSONObject(i)
            val id = categoryObject.getLong("id")
            val resourceIconName = categoryObject.getString("resource_icon")
            val namesArray = categoryObject.getJSONArray("names")
            val category = Category(id, resourceIconName)
            for(j in 0 until namesArray.length()){
                val nameObject = namesArray.getJSONObject(j)
                val name = nameObject.getString("name")
                val locale = nameObject.getString("locale")
                val categoryName = CategoryName(locale = locale, name = name, categoryId = id)
                category.categoryNames.add(categoryName)
            }
            categories.add(category)
        }
        return categories
    }

    private fun getProducts() : List<Product>{
        val productsJson = AssetsHelper.getInstance(context).loadJSON(AssetsHelper.FILE_PRODUCTS)
        val productsArray = JSONArray(productsJson)
        val products = mutableListOf<Product>()
        for(i in 0 until productsArray.length()){
            val productObject = productsArray.getJSONObject(i)
            val productId = i+1L
            val categoryId = productObject.getLong("category_id")
            val namesArray = productObject.getJSONArray("names")
            val product = Product(productId, categoryId)
            for(j in 0 until namesArray.length()){
                val nameObject = namesArray.getJSONObject(j)
                val name = nameObject.getString("name")
                val locale = nameObject.getString("locale")
                val productName = ProductName(locale=locale,name=name,productId=productId)
                product.productNames.add(productName)
            }
            products.add(product)
        }
        return products
    }

    fun initDatabase(db: SQLiteDatabase){
        SQL_CREATE.forEach {
            db.execSQL(it)
        }
        insertCategories(db)
        insertProducts(db)
    }

    fun clearDatabase(db: SQLiteDatabase){
        db.execSQL(DELETE_TEMPLATE.format(Item.TABLE_NAME))
        db.execSQL(DELETE_TEMPLATE.format(ShoppingList.TABLE_NAME))
        db.execSQL(DELETE_TEMPLATE.format(ProductName.TABLE_NAME))
        db.execSQL(DELETE_TEMPLATE.format(Product.TABLE_NAME))
        db.execSQL(DELETE_TEMPLATE.format(CategoryName.TABLE_NAME))
        db.execSQL(DELETE_TEMPLATE.format(Category.TABLE_NAME))
    }

    private fun insertCategories(db: SQLiteDatabase){
        val categories = SQLiteDataHelper.getInstance(context).getCategories()
        categories.forEach { category ->

            db.execSQL(
                INSERT_CATEGORY_TEMPLATE.format(
                    category.id, category.resourceIconName
                ))

            category.categoryNames.forEach { categoryName ->
                db.execSQL(
                    INSERT_CATEGORY_NAME_TEMPLATE.format(
                        categoryName.name, categoryName.locale, categoryName.categoryId
                    ))
            }
        }
    }

    private fun insertProducts(db: SQLiteDatabase){
        val products = SQLiteDataHelper.getInstance(context).getProducts()
        products.forEach { product ->
            db.execSQL(
                INSERT_PRODUCT_TEMPLATE.format(
                    product.id, product.categoryId
                ))
            product.productNames.forEach { productName ->
                db.execSQL(
                    INSERT_PRODUCT_NAME_TEMPLATE.format(
                        productName.name, productName.locale, productName.productId
                    ))
            }
        }
    }

}