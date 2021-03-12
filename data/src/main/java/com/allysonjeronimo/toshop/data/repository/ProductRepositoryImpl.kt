package com.allysonjeronimo.toshop.data.repository

import com.allysonjeronimo.toshop.data.db.dao.ProductDao
import com.allysonjeronimo.toshop.data.db.entity.ProductWithName
import com.allysonjeronimo.toshop.data.mapper.Mapper
import com.allysonjeronimo.toshop.domain.entity.Product
import com.allysonjeronimo.toshop.domain.repository.ProductRepository

internal class ProductRepositoryImpl(
    private val dao : ProductDao,
    private val toModelMapper: Mapper<ProductWithName, Product>
) : ProductRepository {

    override suspend fun search(term: String, locale: String): List<Product> {
        return dao.search(term, locale).map{toModelMapper.map(it)}
    }
}