package com.allysonjeronimo.toshop.data.repository

import com.allysonjeronimo.toshop.data.db.dao.CategoryDao
import com.allysonjeronimo.toshop.data.db.entity.CategoryWithName
import com.allysonjeronimo.toshop.data.mapper.Mapper
import com.allysonjeronimo.toshop.domain.entity.Category
import com.allysonjeronimo.toshop.domain.repository.CategoryRepository

internal class CategoryRepositoryImpl(
    private val dao: CategoryDao,
    private val toModelMapper: Mapper<CategoryWithName, Category>
) : CategoryRepository {

    override suspend fun findAll(locale:String): List<Category> {
        return if(count(locale) > 0)
            dao.findAll(locale).map { toModelMapper.map(it) }
        else
            dao.findAll("en-US").map { toModelMapper.map(it) }
    }

    override suspend fun count(locale: String) =
        dao.count(locale)
}