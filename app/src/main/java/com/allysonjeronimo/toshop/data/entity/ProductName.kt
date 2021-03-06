package com.allysonjeronimo.toshop.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductName(
    @PrimaryKey(autoGenerate = true)
    val id:Long = 0L,
    val locale:String,
    val name:String,
    @ColumnInfo(name="product_id")
    val productId:Long
)