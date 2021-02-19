package com.allysonjeronimo.toshop.data.entity

import androidx.room.ColumnInfo
import androidx.room.Ignore

data class ProductWithName (
    var id:Long = 0L,
    @ColumnInfo(name="category_id")
    var categoryId: Long,
    var name:String
){

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
