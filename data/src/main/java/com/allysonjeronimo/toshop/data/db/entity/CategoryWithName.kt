package com.allysonjeronimo.toshop.data.db.entity

import androidx.room.ColumnInfo

internal data class CategoryWithName(
    var id:Long = 0L,
    @ColumnInfo(name="resource_icon_name")
    var resourceIconName:String,
    var name:String
)