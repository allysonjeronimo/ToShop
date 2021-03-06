package com.allysonjeronimo.toshop.data.repository

import com.allysonjeronimo.toshop.data.db.entity.Product
import com.allysonjeronimo.toshop.data.db.entity.ProductWithName

interface ProductRepository {

    suspend fun insertWithNames(products:List<Product>)

    suspend fun search(term:String, locale:String) : List<ProductWithName>
}