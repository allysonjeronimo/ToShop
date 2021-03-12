package com.allysonjeronimo.toshop.domain.entity

import java.util.*

data class ShoppingList(
    var id:Long = 0L,
    var description:String,
    var lastUpdate:Date = Date(),
    var quantityPurchasedItems:Int = 0,
    var quantityItems:Int = 0,
    var total:Double = 0.0)