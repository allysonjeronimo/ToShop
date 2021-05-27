package com.allysonjeronimo.toshop.domain.interactor.product

import com.allysonjeronimo.toshop.domain.entity.Product
import com.allysonjeronimo.toshop.domain.repository.ProductRepository

class SearchProductsUseCase(private val repository: ProductRepository) {

    suspend fun execute(term:String,locale:String) : List<Product>{
        return repository.search(term, locale)
    }
}