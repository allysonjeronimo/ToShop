package com.allysonjeronimo.toshop.domain.interactor.item.data

import com.allysonjeronimo.toshop.domain.entity.Item
import java.util.*

object ItemEntityFactory {

    fun dummyItemsList() = listOf(
        Item(
            1,
            "Item 1",
            1.0,
            "un",
            1.99,
            "",
            false,
            Date(),
            1L,
            1L,
            ""
        ),
        Item(
            2,
            "Item 2",
            1.0,
            "un",
            1.99,
            "",
            false,
            Date(),
            1L,
            1L,
            ""
        ),
        Item(
            3,
            "Item 3",
            1.0,
            "un",
            1.99,
            "",
            false,
            Date(),
            1L,
            1L,
            ""
        ),
    )

    fun dummyItem() = Item(
        1,
        "Item 1",
        1.0,
        "",
        0.0,
        "",
        false,
        Date(),
        0,
        1,
        ""
    )
}