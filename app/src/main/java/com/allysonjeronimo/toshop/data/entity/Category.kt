package com.allysonjeronimo.toshop.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category (
    @PrimaryKey
    var id:Long = 0L,
    @ColumnInfo(name="resource_icon_name")
    var resourceIconName:String
) {
    var name:String? = null
    var categoryNames = mutableListOf<CategoryName>()
}