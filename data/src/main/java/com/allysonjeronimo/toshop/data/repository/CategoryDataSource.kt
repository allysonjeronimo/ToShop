package com.allysonjeronimo.toshop.data.repository

import com.allysonjeronimo.toshop.data.db.dao.CategoryDao
import com.allysonjeronimo.toshop.data.db.entity.Category
import com.allysonjeronimo.toshop.data.db.entity.CategoryWithName

class CategoryDataSource(
    private val dao: CategoryDao
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