package com.allysonjeronimo.toshop.repository

import com.allysonjeronimo.toshop.data.dao.CategoryDao
import com.allysonjeronimo.toshop.data.entity.Category
import com.allysonjeronimo.toshop.data.entity.CategoryName
import com.allysonjeronimo.toshop.data.entity.CategoryWithName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoryDataSource(
    private val dao:CategoryDao
) : CategoryRepository{

    override suspend fun insertAll(categories: List<Category>) {
        dao.insertAll(categories)
    }

    override suspend fun insertWithNames(categories: List<Category>) {
        dao.insertWithNames(categories)
    }

    override suspend fun findAll(locale:String): List<CategoryWithName> {
        return dao.findAll(locale)
    }
}