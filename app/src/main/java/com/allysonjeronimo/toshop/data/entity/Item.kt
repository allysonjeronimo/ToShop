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
    var description:String? = null,
    var quantity:Double = 1.0,
    var unit:String? = null,
    var price:Double = 0.0,
    var notes:String = "",
    var purchased:Boolean = false,
    @ColumnInfo(name="last_update", defaultValue = "(datetime('now','localtime'))")
    var lastUpdate: Date? = Date(),
    @ColumnInfo(name="category_id")
    var categoryId: Long = 1,
    @ColumnInfo(name="list_id")
    var listId: Long = 0L
    )