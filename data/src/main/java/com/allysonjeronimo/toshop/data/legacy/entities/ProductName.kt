package com.allysonjeronimo.toshop.data.legacy.entities

data class ProductName(
    val id:Long = 0L,
    val locale:String,
    val name:String,
    val productId:Long
) {
    companion object{
        const val TABLE_NAME = "ProductName"
        const val COLUMN_ID = "id"
        const val COLUMN_LOCALE = "locale"
        const val COLUMN_NAME = "name"
        const val FK_COLUMN_PRODUCT_ID = "product_id"
    }
}