package com.allysonjeronimo.toshop.data.repository

import com.allysonjeronimo.toshop.data.db.entity.Category
import com.allysonjeronimo.toshop.data.db.entity.CategoryWithName

interface CategoryRepository {

    suspend fun insertAll(categories:List<Category>)

    suspend fun insertWithNames(categories:List<Category>)

    suspend fun findAll(locale:String) : List<CategoryWithName>

    suspend fun count(locale:String) : Int
}