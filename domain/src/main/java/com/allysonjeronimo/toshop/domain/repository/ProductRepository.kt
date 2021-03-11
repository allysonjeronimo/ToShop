package com.allysonjeronimo.toshop.domain.repository

import com.allysonjeronimo.toshop.domain.entity.Product

interface ProductRepository {
    suspend fun search(term:String, locale:String) : List<Product>
}