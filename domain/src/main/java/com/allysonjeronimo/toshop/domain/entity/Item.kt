package com.allysonjeronimo.toshop.domain.entity

import java.util.*

data class Item(
    val id:Long,
    val description:String,
    val quantity:Double,
    val unit:String?,
    val price:Double,
    val notes:String?,
    val purchased:Boolean,
    val lastUpdate:Date,
    val categoryId:Long,
    val listId:Long,
    val categoryResourceIcon:String
    )