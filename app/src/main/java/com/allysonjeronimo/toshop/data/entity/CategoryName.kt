package com.allysonjeronimo.toshop.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryName (
    @PrimaryKey(autoGenerate = true)
    val id:Long = 0L,
    val locale:String,
    val name:String,
    @ColumnInfo(name="category_id")
    val categoryId:Long
)