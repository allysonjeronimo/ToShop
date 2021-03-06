package com.allysonjeronimo.toshop.data.legacy.repositories

import com.allysonjeronimo.toshop.data.legacy.entities.Product

interface ProductRepository {
    fun search(term:String) : List<Product>
}