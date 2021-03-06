package com.allysonjeronimo.toshop.data.db.entity

import androidx.room.ColumnInfo
import java.util.*

data class ShoppingListWithDetails(
    var id:Long = 0L,
    var description:String = "",
    @ColumnInfo(name="last_update")
    var lastUpdate: Date? = Date(),
    @ColumnInfo(name="quantity_purchased_items")
    var quantityPurchasedItems:Int = 0,
    @ColumnInfo(name="quantity_items")
    var quantityItems:Int = 0,
    var total:Double = 0.0,
)