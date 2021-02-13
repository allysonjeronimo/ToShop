package com.allysonjeronimo.toshop.legacy.repositories

import com.allysonjeronimo.toshop.legacy.entities.Product

interface ProductRepository {
    fun search(term:String) : List<Product>
}