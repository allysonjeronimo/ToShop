package com.allysonjeronimo.toshop.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "Product")
internal data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Long = 0L,
    @ColumnInfo(name="category_id")
    val categoryId: Long
){
    @Ignore
    var productNames = mutableListOf<ProductNameEntity>()
}