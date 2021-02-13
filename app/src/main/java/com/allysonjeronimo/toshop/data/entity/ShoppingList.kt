package com.allysonjeronimo.toshop.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class ShoppingList(
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0L,
    var description:String = "",
    @ColumnInfo(name="last_update")
    var lastUpdate:Date = Date()) {

    var quantityPurchasedItems:Int = 0
    var quantityItems:Int = 0
    var total:Double = 0.0
}