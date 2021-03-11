package com.allysonjeronimo.toshop.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "Category")
internal data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0L,
    @ColumnInfo(name="resource_icon_name")
    var resourceIconName:String
){
    @Ignore
    var categoryNames = mutableListOf<CategoryNameEntity>()
}