package com.allysonjeronimo.toshop.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Ignore

internal data class ProductWithName (
    var id:Long = 0L,
    @ColumnInfo(name="category_id")
    var categoryId: Long,
    var name:String
){

    @Ignore
    var isNew:Boolean = false

    fun getItem(shoppingListId:Long): ItemEntity {
        return ItemEntity(
            description = this.name,
            categoryId = this.categoryId,
            listId = shoppingListId
        )
    }

    override fun toString(): String = name ?: ""
}
