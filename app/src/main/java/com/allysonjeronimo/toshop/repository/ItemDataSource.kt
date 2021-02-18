package com.allysonjeronimo.toshop.repository

import com.allysonjeronimo.toshop.data.dao.ItemDao
import com.allysonjeronimo.toshop.data.entity.Item
import com.allysonjeronimo.toshop.data.entity.ItemWithCategoryIcon

class ItemDataSource (
    private val dao : ItemDao
    ) : ItemRepository{

    override suspend fun save(item: Item) {
        dao.save(item)
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
        dao.findByShoppingList(shoppingListId)


    override suspend fun countByShoppingList(shoppingListId: Long) =
        dao.countByShoppingList(shoppingListId)

    override suspend fun count(): Int {
        return dao.count()
    }
}