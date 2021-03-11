package com.allysonjeronimo.toshop.data.repository

import com.allysonjeronimo.toshop.data.db.dao.ShoppingListDao
import com.allysonjeronimo.toshop.data.db.entity.ShoppingListEntity
import com.allysonjeronimo.toshop.data.db.entity.ShoppingListWithDetails
import com.allysonjeronimo.toshop.data.mapper.Mapper
import com.allysonjeronimo.toshop.domain.entity.ShoppingList
import com.allysonjeronimo.toshop.domain.repository.ShoppingListRepository

internal class ShoppingListRepositoryImpl(
    private val dao: ShoppingListDao,
    private val toModelMapper: Mapper<ShoppingListWithDetails, ShoppingList>,
    private val toEntityMapper: Mapper<ShoppingList, ShoppingListEntity>
) : ShoppingListRepository {

    override suspend fun save(shoppingList: ShoppingList) {
        dao.save(toEntityMapper.map(shoppingList))
    }

    override suspend fun delete(id: Long) {
        dao.delete(id)
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }

    override suspend fun search(term: String): List<ShoppingList> {
        return if(term.isEmpty())
            dao.findAll().map{toModelMapper.map(it)}
        else
            dao.search("%$term%").map{toModelMapper.map(it)}
    }

    override suspend fun count() = dao.count()
}