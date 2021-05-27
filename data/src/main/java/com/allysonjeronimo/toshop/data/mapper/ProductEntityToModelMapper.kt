package com.allysonjeronimo.toshop.data.mapper

import com.allysonjeronimo.toshop.data.db.entity.ProductWithName
import com.allysonjeronimo.toshop.domain.entity.Product

internal class ProductEntityToModelMapper : Mapper<ProductWithName, Product>{

    override fun map(source: ProductWithName): Product {
        return Product(
            id = source.id,
            name = source.name,
            categoryId = source.categoryId
        )
    }

}