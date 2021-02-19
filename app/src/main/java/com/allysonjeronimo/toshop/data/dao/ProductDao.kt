package com.allysonjeronimo.toshop.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.allysonjeronimo.toshop.data.entity.Product
import com.allysonjeronimo.toshop.data.entity.ProductName
import com.allysonjeronimo.toshop.data.entity.ProductWithName

@Dao
abstract class ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(products:List<Product>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertNames(productNames:List<ProductName>)

    @Query("""
        SELECT p.id, p.category_id, pn.name 
        FROM Product p, ProductName pn
        WHERE p.id = pn.product_id AND 
        pn.name like :term AND
        pn.locale = :locale
        ORDER BY pn.name ASC
    """)
    abstract suspend fun search(term:String, locale:String) : List<ProductWithName>

    suspend fun insertWithNames(products: List<Product>){
        val productNames = products.flatMap { it.productNames!! }
        insertAll(products)
        insertNames(productNames)
    }
}