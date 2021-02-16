package com.allysonjeronimo.toshop.repository

import com.allysonjeronimo.toshop.data.entity.Category
import com.allysonjeronimo.toshop.data.entity.CategoryWithName

interface CategoryRepository {

    suspend fun insertWithNames(categories:List<Category>)

    suspend fun findAll(locale:String) : List<CategoryWithName>

}