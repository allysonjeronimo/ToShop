package com.allysonjeronimo.toshop.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName="List")
data class ShoppingList(
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0L,
    var description:String = "",
    @ColumnInfo(name="last_update", defaultValue = "CURRENT_TIMESTAMP")
    var lastUpdate:Date = Date()
)