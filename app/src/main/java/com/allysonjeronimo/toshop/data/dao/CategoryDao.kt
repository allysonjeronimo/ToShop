package com.allysonjeronimo.toshop.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.allysonjeronimo.toshop.data.entity.Category

@Dao
interface CategoryDao {

    @Query("""
        SELECT ct.id, ct.resource_icon_name, cn.name 
        FROM Category ct, CategoryName cn 
        WHERE ct.id = cn.category_id AND cn.locale = :locale 
        ORDER BY ct.id ASC
    """)
    suspend fun findAll(locale:String) : List<Category>

}