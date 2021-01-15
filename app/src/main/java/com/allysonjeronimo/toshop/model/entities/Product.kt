package com.allysonjeronimo.toshop.model.entities

import com.allysonjeronimo.toshop.R
import kotlin.collections.List

data class Product (
    val id:Long = 0L,
    val categoryId: Long,
    val name:String? = null
){

    var category:Category? = null
    var isNew:Boolean = false
    val productNames = mutableListOf<ProductName>()

    fun getItem(shoppingListId:Long): Item {
        return Item(
            description = this.name,
            categoryId = this.categoryId,
            listId = shoppingListId
        )
    }

    companion object{
        const val TABLE_NAME = "Product"
        const val COLUMN_ID = "id"
        const val FK_COLUMN_CATEGORY_ID = "category_id"
        const val TRANSIENT_NAME = "name"
    }

    override fun toString(): String = name ?: ""


}