package com.allysonjeronimo.toshop.domain.interactor.category.data

import com.allysonjeronimo.toshop.domain.entity.Category

object CategoryEntityFactory {

    fun dummyCategoryList() = listOf(
        Category(
            1,
            "",
            "Category 1"
        ),
        Category(
            2,
            "",
            "Category 2"
        ),
        Category(
            3,
            "",
            "Category 3"
        )
    )
}