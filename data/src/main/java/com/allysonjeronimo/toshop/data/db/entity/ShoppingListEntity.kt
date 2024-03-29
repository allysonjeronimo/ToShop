package com.allysonjeronimo.toshop.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName="List")
internal data class ShoppingListEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0L,
    var description:String = "",
    @ColumnInfo(name="last_update", defaultValue = "CURRENT_TIMESTAMP")
    var lastUpdate:Date = Date()
)