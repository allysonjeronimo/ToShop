package com.allysonjeronimo.toshop.repository

import com.allysonjeronimo.toshop.data.entity.Product
import com.allysonjeronimo.toshop.data.entity.ProductWithName

interface ProductRepository {

    suspend fun insertWithNames(products:List<Product>)

    suspend fun search(term:String, locale:String) : List<ProductWithName>
}