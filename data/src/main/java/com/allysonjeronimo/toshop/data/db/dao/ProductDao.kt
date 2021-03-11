package com.allysonjeronimo.toshop.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.allysonjeronimo.toshop.data.db.entity.ProductEntity
import com.allysonjeronimo.toshop.data.db.entity.ProductNameEntity
import com.allysonjeronimo.toshop.data.db.entity.ProductWithName

@Dao
internal abstract class ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(products:List<ProductEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertNames(productNames:List<ProductNameEntity>)

    @Query("""
        SELECT p.id, p.category_id, pn.name 
        FROM Product p, ProductName pn
        WHERE p.id = pn.product_id AND 
        pn.name like :term AND
        pn.locale = :locale
        ORDER BY pn.name ASC
    """)
    abstract suspend fun search(term:String, locale:String) : List<ProductWithName>

    suspend fun insertWithNames(products: List<ProductEntity>){
        val productNames = products.flatMap { it.productNames!! }
        insertAll(products)
        insertNames(productNames)
    }
}