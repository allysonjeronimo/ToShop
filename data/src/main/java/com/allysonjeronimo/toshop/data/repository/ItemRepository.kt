package com.allysonjeronimo.toshop.data.repository

import com.allysonjeronimo.toshop.data.db.entity.Item
import com.allysonjeronimo.toshop.data.db.entity.ItemWithCategoryIcon

interface ItemRepository {

    suspend fun save(item: Item)

    suspend fun delete(id:Long)

    suspend fun deleteByShoppingList(shoppingListId:Long)

    suspend fun updatePurchasedByShoppingList(purchased:Boolean, shoppingListId: Long)

    suspend fun findByShoppingList(shoppingListId: Long) : List<ItemWithCategoryIcon>

    suspend fun countByShoppingList(shoppingListId: Long) : Int

    suspend fun count() : Int
}