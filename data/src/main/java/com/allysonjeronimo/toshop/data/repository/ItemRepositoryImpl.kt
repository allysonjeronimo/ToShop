package com.allysonjeronimo.toshop.data.repository

import com.allysonjeronimo.toshop.data.db.dao.ItemDao
import com.allysonjeronimo.toshop.data.db.entity.ItemEntity
import com.allysonjeronimo.toshop.data.db.entity.ItemWithCategoryIcon
import com.allysonjeronimo.toshop.data.mapper.Mapper
import com.allysonjeronimo.toshop.domain.entity.Item
import com.allysonjeronimo.toshop.domain.repository.ItemRepository

internal class ItemRepositoryImpl (
    private val dao : ItemDao,
    private val toModelMapper: Mapper<ItemWithCategoryIcon, Item>,
    private val toEntityMapper: Mapper<Item, ItemEntity>
    ) : ItemRepository {

    override suspend fun save(item: Item) {
        dao.save(toEntityMapper.map(item))
    }

    override suspend fun delete(id: Long) {
        dao.delete(id)
    }

    override suspend fun deleteByShoppingList(shoppingListId: Long) {
        dao.deleteByShoppingList(shoppingListId)
    }

    override suspend fun updatePurchasedByShoppingList(purchased: Boolean, shoppingListId: Long) {
        dao.updatePurchasedByShoppingList(purchased, shoppingListId)
    }

    override suspend fun findByShoppingList(shoppingListId: Long) =
        dao.findByShoppingList(shoppingListId).map { toModelMapper.map(it) }


    override suspend fun countByShoppingList(shoppingListId: Long) =
        dao.countByShoppingList(shoppingListId)

    override suspend fun count(): Int {
        return dao.count()
    }
}