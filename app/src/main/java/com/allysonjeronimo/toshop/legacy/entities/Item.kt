package com.allysonjeronimo.toshop.legacy.entities

import android.os.Parcelable
import com.allysonjeronimo.toshop.data.legacy.entities.Category
import com.allysonjeronimo.toshop.legacy.utils.toCurrency
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Item(
    var id:Long = 0L,
    var description:String? = null,
    var quantity:Double = 1.0,
    var unit:String? = null,
    var price:Double = 0.0,
    var notes:String = "",
    var purchased:Boolean = false,
    var lastUpdate: Date = Date(),
    var categoryId: Long = 1,
    var listId: Long = 0L,
    var category: Category? = null
    ) : Parcelable{

    val details:String
        get(){
            return "$quantity $unit, ${price.toCurrency()}"
        }

    companion object{
        const val TABLE_NAME = "Item"
        const val COLUMN_ID = "id"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_QUANTITY = "quantity"
        const val COLUMN_UNIT = "unit"
        const val COLUMN_PRICE = "price"
        const val COLUMN_NOTES = "notes"
        const val COLUMN_PURCHASED = "purchased"
        const val COLUMN_LAST_UPDATE = "last_update"
        const val FK_CATEGORY_ID = "category_id"
        const val FK_COLUMN_LIST_ID = "list_id"
    }
}