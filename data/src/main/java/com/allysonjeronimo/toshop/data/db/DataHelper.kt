package com.allysonjeronimo.toshop.data.db

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import com.allysonjeronimo.toshop.common.extensions.loadFileFromAssets
import com.allysonjeronimo.toshop.data.db.entity.CategoryEntity
import com.allysonjeronimo.toshop.data.db.entity.CategoryNameEntity
import com.allysonjeronimo.toshop.data.db.entity.ProductEntity
import com.allysonjeronimo.toshop.data.db.entity.ProductNameEntity
import org.json.JSONArray

class DataHelper(
    private val context: Context)
{
    companion object{

        const val FILE_CATEGORIES = "categories.json"
        const val FILE_PRODUCTS = "products.json"

        private var instance: DataHelper? = null

        fun getInstance(context: Context) : DataHelper {
            if(instance == null)
                instance = DataHelper(context)
            return instance!!
        }

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
    }

    fun initDatabase(db: SupportSQLiteDatabase){
        try{
            if(!hasCategoryData(db)){
                insertCategories(db)
            }
            if(!hasProductData(db)){
                insertProducts(db)
            }
        }catch(ex:Exception){
            ex.printStackTrace()
        }
    }

    private fun getCategories() : List<CategoryEntity>{
        val categoriesJson = context.loadFileFromAssets(FILE_CATEGORIES)
        val categoriesArray = JSONArray(categoriesJson)
        val categories = mutableListOf<CategoryEntity>()
        for(i in 0 until categoriesArray.length()){
            val categoryObject = categoriesArray.getJSONObject(i)
            val id = categoryObject.getLong("id")
            val resourceIconName = categoryObject.getString("resource_icon")
            val namesArray = categoryObject.getJSONArray("names")
            val category = CategoryEntity(id, resourceIconName)
            for(j in 0 until namesArray.length()){
                val nameObject = namesArray.getJSONObject(j)
                val name = nameObject.getString("name")
                val locale = nameObject.getString("locale")
                val categoryName = CategoryNameEntity(locale = locale, name = name, categoryId = id)
                category.categoryNames.add(categoryName)
            }
            categories.add(category)
        }
        return categories
    }

    private fun getProducts() : List<ProductEntity>{
        val productsJson = context.loadFileFromAssets(FILE_PRODUCTS)
        val productsArray = JSONArray(productsJson)
        val products = mutableListOf<ProductEntity>()
        for(i in 0 until productsArray.length()){
            val productObject = productsArray.getJSONObject(i)
            val productId = i+1L
            val categoryId = productObject.getLong("category_id")
            val namesArray = productObject.getJSONArray("names")
            val product = ProductEntity(productId, categoryId)
            for(j in 0 until namesArray.length()){
                val nameObject = namesArray.getJSONObject(j)
                val name = nameObject.getString("name")
                val locale = nameObject.getString("locale")
                val productName = ProductNameEntity(locale=locale,name=name,productId=productId)
                product.productNames.add(productName)
            }
            products.add(product)
        }
        return products
    }

    private fun insertCategories(db: SupportSQLiteDatabase){
        val categories = getCategories()
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

    private fun insertProducts(db: SupportSQLiteDatabase){
        val products = getProducts()
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

    private fun hasCategoryData(db: SupportSQLiteDatabase) : Boolean{
        val cursor = db.query("SELECT Count(*) FROM Category")
        var result = 0
        if(cursor.moveToFirst())
            result = cursor.getInt(0)
        cursor.close()
        return result > 0
    }

    private fun hasProductData(db: SupportSQLiteDatabase) : Boolean{
        val cursor = db.query("SELECT Count(*) FROM Product")
        var result = 0
        if(cursor.moveToFirst())
            result = cursor.getInt(0)
        cursor.close()
        return result > 0
    }
}