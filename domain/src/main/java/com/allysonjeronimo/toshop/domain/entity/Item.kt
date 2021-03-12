package com.allysonjeronimo.toshop.domain.entity

import java.util.*

data class Item(
    var id:Long = 0L,
    var description:String,
    var quantity:Double,
    var unit:String? = null,
    var price:Double,
    var notes:String? = "",
    var purchased:Boolean,
    var lastUpdate:Date = Date(),
    var categoryId:Long = 0L,
    var listId:Long = 0L,
    var categoryResourceIcon:String = ""
    )