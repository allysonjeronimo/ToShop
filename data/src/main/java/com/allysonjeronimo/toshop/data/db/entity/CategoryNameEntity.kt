package com.allysonjeronimo.toshop.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CategoryName")
internal data class CategoryNameEntity (
    @PrimaryKey(autoGenerate = true)
    val id:Long = 0L,
    val locale:String,
    val name:String,
    @ColumnInfo(name="category_id")
    val categoryId:Long
)