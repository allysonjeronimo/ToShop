package com.allysonjeronimo.toshop.domain.interactor.product.data

import com.allysonjeronimo.toshop.domain.entity.Product

object ProductEntityFactory {

    fun dummyProductList() = listOf(
        Product(
            1,
            "Product 1",
            1
        ),
        Product(
            2,
            "Product 2",
            1
        ),
        Product(
            3,
            "Product 3",
            1
        )
    )
}