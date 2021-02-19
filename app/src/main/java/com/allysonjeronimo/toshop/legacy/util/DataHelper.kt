package com.allysonjeronimo.toshop.legacy.util

import android.content.Context
import com.allysonjeronimo.toshop.legacy.entities.Category
import com.allysonjeronimo.toshop.legacy.entities.CategoryName
import com.allysonjeronimo.toshop.legacy.entities.Product
import com.allysonjeronimo.toshop.legacy.entities.ProductName
import com.allysonjeronimo.toshop.legacy.utils.AssetsHelper
import org.json.JSONArray

class DataHelper(private val context:Context){

    companion object{

        private var instance: DataHelper? = null

        fun getInstance(context: Context) : DataHelper {
            if(instance == null)
                instance = DataHelper(context)
            return instance!!
        }
    }

    fun getCategories() : List<Category>{
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

    fun getProducts() : List<Product>{
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

}