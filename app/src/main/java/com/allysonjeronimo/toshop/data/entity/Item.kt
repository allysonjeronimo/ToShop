package com.allysonjeronimo.toshop.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    foreignKeys = [
        ForeignKey(
                entity = ShoppingList::class,
                parentColumns = ["id"],
                childColumns = ["list_id"],
                onDelete = CASCADE
        )
    ]
)
data class Item(
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0L,
    var description:String,
    @ColumnInfo(defaultValue = "1")
    var quantity:Double? = 1.0,
    @ColumnInfo(defaultValue = "UN")
    var unit:String? = null,
    @ColumnInfo(defaultValue = "0.0")
    var price:Double = 0.0,
    var notes:String = "",
    @ColumnInfo(defaultValue = "0")
    var purchased:Boolean = false,
    @ColumnInfo(name="last_update", defaultValue = "CURRENT_TIMESTAMP")
    var lastUpdate: Date? = Date(),
    @ColumnInfo(name="category_id", defaultValue = "1")
    var categoryId: Long = 1,
    @ColumnInfo(name="list_id")
    var listId: Long = 0L
    )