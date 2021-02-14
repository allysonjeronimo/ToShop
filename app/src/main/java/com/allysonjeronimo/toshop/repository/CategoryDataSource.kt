package com.allysonjeronimo.toshop.repository

import com.allysonjeronimo.toshop.data.dao.CategoryDao
import com.allysonjeronimo.toshop.data.entity.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoryDataSource(
    private val dao:CategoryDao
) : CategoryRepository{

    override suspend fun findAll(locale:String): List<Category> {
        return withContext(Dispatchers.IO){
            dao.findAll(locale)
        }
    }
}