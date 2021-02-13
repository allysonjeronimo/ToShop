package com.allysonjeronimo.toshop.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product (
    @PrimaryKey
    val id:Long = 0L,
    @ColumnInfo(name="category_id")
    val categoryId: Long
){

    val name:String? = null
    var category: Category? = null
    var isNew:Boolean = false
    val productNames = mutableListOf<ProductName>()

    fun getItem(shoppingListId:Long): Item {
        return Item(
            description = this.name,
            categoryId = this.categoryId,
            listId = shoppingListId
        )
    }
    override fun toString(): String = name ?: ""
}