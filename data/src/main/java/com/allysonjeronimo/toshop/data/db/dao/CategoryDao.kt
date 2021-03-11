package com.allysonjeronimo.toshop.data.db.dao

import androidx.room.*
import com.allysonjeronimo.toshop.data.db.entity.CategoryEntity
import com.allysonjeronimo.toshop.data.db.entity.CategoryNameEntity
import com.allysonjeronimo.toshop.data.db.entity.CategoryWithName

@Dao
internal abstract class CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(categories:List<CategoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertNames(categoryNames:List<CategoryNameEntity>)

    @Query("SELECT Count(cn.id) FROM CategoryName cn WHERE cn.locale = :locale")
    abstract suspend fun count(locale:String) : Int

    @Query("""
        SELECT ct.id, ct.resource_icon_name, cn.name 
        FROM Category ct, CategoryName cn 
        WHERE ct.id = cn.category_id AND cn.locale = :locale 
        ORDER BY ct.id ASC
    """)
    abstract suspend fun findAll(locale:String) : List<CategoryWithName>

    suspend fun insertWithNames(categories:List<CategoryEntity>){
        val categoryNames = categories.flatMap { it.categoryNames!! }
        insertAll(categories)
        insertNames(categoryNames)
    }

}