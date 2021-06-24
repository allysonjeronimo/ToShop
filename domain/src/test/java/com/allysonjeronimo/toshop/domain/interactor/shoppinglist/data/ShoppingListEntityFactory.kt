package com.allysonjeronimo.toshop.domain.interactor.shoppinglist.data

import com.allysonjeronimo.toshop.domain.entity.ShoppingList
import java.util.*

object ShoppingListEntityFactory {

    fun dummyShoppingListList() = listOf(
        ShoppingList(
        1L,
        "Shopping List 1",
        Date(),
        0,
        0,
        0.0
        ),
        ShoppingList(
            2L,
            "Shopping List 2",
            Date(),
            0,
            0,
            0.0
        ),
        ShoppingList(
            3L,
            "Shopping List 3",
            Date(),
            0,
            0,
            0.0
        )
    )


    fun dummyShoppingList() = ShoppingList(
        1L,
        "Shopping List 1",
        Date(),
        0,
        0,
        0.0
    )
}