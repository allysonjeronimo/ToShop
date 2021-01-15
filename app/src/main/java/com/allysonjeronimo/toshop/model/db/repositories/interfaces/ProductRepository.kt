package com.allysonjeronimo.toshop.model.db.repositories.interfaces

import com.allysonjeronimo.toshop.model.entities.Product

interface ProductRepository {
    fun search(term:String) : List<Product>
}