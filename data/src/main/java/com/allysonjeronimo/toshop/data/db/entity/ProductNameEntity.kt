package com.allysonjeronimo.toshop.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ProductName")
internal data class ProductNameEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Long = 0L,
    val locale:String,
    val name:String,
    @ColumnInfo(name="product_id")
    val productId:Long
)