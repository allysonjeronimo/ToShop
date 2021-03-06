package com.allysonjeronimo.toshop.data.dao

import androidx.room.*
import com.allysonjeronimo.toshop.data.entity.Category
import com.allysonjeronimo.toshop.data.entity.CategoryName
import com.allysonjeronimo.toshop.data.entity.CategoryWithName

@Dao
abstract class CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(categories:List<Category>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertNames(categoryNames:List<CategoryName>)

    @Query("""
        SELECT ct.id, ct.resource_icon_name, cn.name 
        FROM Category ct, CategoryName cn 
        WHERE ct.id = cn.category_id AND cn.locale = :locale 
        ORDER BY ct.id ASC
    """)
    abstract suspend fun findAll(locale:String) : List<CategoryWithName>

    suspend fun insertWithNames(categories:List<Category>){
        val categoryNames = categories.flatMap { it.categoryNames!! }
        insertAll(categories)
        insertNames(categoryNames)
    }

}