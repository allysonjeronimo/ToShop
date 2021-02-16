package com.allysonjeronimo.toshop.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey
    val id:Long = 0L,
    @ColumnInfo(name="category_id")
    val categoryId: Long
){

    @Ignore
    var name:String? = null

    @Ignore
    var isNew:Boolean = false

    fun getItem(shoppingListId:Long): Item {
        return Item(
            description = this.name,
            categoryId = this.categoryId,
            listId = shoppingListId
        )
    }

    override fun toString(): String = name ?: ""
}