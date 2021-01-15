package com.allysonjeronimo.toshop.model.entities

import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class ShoppingList(
    var id:Long = 0L,
    var description:String = "",
    var lastUpdate:Date = Date()) : Parcelable {

    @IgnoredOnParcel
    var quantityPurchasedItems:Int = 0
    @IgnoredOnParcel
    var quantityItems:Int = 0
    @IgnoredOnParcel
    var total:Double = 0.0

    companion object{
        const val TABLE_NAME:String = "List"
        const val COLUMN_ID:String = "id"
        const val COLUMN_DESCRIPTION:String = "description"
        const val COLUMN_LAST_UPDATE = "last_update"
        const val TRANSIENT_QUANTITY_PURCHASED_ITEMS = "quantity_purchased_items"
        const val TRANSIENT_QUANTITY_ITEMS = "quantity_items"
        const val TRANSIENT_TOTAL = "total"
    }

}