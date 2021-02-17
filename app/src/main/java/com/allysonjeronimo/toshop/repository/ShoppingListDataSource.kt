package com.allysonjeronimo.toshop.repository

import com.allysonjeronimo.toshop.data.dao.ShoppingListDao
import com.allysonjeronimo.toshop.data.entity.ShoppingList
import com.allysonjeronimo.toshop.data.entity.ShoppingListWithDetails

class ShoppingListDataSource(
    private val dao:ShoppingListDao
) : ShoppingListRepository{

    override suspend fun save(shoppingList: ShoppingList) {
        dao.save(shoppingList)
    }

    override suspend fun delete(id: Long) {
        dao.delete(id)
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }

    override suspend fun search(term: String): List<ShoppingListWithDetails> {
        return if(term.isEmpty())
            dao.findAll()
        else
            dao.search("%$term%")
    }

    override suspend fun count() = dao.count()
}