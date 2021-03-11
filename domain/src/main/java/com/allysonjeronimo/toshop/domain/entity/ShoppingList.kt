package com.allysonjeronimo.toshop.domain.entity

import java.util.*

data class ShoppingList(
    val id:Long,
    val description:String,
    val lastUpdate:Date,
    val quantityPurchasedItems:Int,
    val quantityItems:Int,
    val total:Double)