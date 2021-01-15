package com.allysonjeronimo.toshop.model.entities

import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category (
    var id:Long = 0L,
    var resourceIconName:String,
    var name:String? = null
)  : Parcelable {

    @IgnoredOnParcel
    var categoryNames = mutableListOf<CategoryName>()

    companion object{
        const val TABLE_NAME = "Category"
        const val COLUMN_ID = "id"
        const val COLUMN_RESOURCE_ICON_NAME = "resource_icon_name"
        const val TRANSIENT_NAME = "name"
    }
}