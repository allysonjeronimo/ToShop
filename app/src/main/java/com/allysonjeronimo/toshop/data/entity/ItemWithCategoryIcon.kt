package com.allysonjeronimo.toshop.data.entity

import androidx.room.ColumnInfo
import com.allysonjeronimo.toshop.legacy.utils.toCurrency
import java.util.*

data class ItemWithCategoryIcon(
    var id:Long = 0L,
    var description:String? = null,
    var quantity:Double = 1.0,
    var unit:String? = null,
    var price:Double = 0.0,
    var notes:String = "",
    var purchased:Boolean = false,
    @ColumnInfo(name="last_update")
    var lastUpdate: Date,
    @ColumnInfo(name="category_id")
    var categoryId: Long = 1,
    @ColumnInfo(name="list_id")
    var listId: Long = 0L,
    @ColumnInfo(name="resource_icon_name")
    var categoryResourceIcon: String
){

    val details:String
        get(){
            return "$quantity $unit, ${price.toCurrency()}"
        }
}