package com.allysonjeronimo.toshop.repository

import com.allysonjeronimo.toshop.data.dao.ProductDao
import com.allysonjeronimo.toshop.data.entity.Product
import com.allysonjeronimo.toshop.data.entity.ProductWithName

class ProductDataSource(
    private val dao : ProductDao
) : ProductRepository{

    override suspend fun insertWithNames(products: List<Product>) {
        dao.insertWithNames(products)
    }

    override suspend fun search(term: String, locale: String): List<ProductWithName> {
        return dao.search("%$term%", locale)
    }
}