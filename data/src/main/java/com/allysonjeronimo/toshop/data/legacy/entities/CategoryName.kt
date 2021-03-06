package com.allysonjeronimo.toshop.data.legacy.entities

data class CategoryName (
    val id:Long = 0L,
    val locale:String,
    val name:String,
    val categoryId:Long
){
    companion object{
        const val TABLE_NAME = "CategoryName"
        const val COLUMN_ID = "id"
        const val COLUMN_LOCALE = "locale"
        const val COLUMN_NAME = "name"
        const val FK_COLUMN_CATEGORY_ID = "category_id"
    }
}