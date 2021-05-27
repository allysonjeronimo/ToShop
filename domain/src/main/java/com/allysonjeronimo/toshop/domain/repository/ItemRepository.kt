package com.allysonjeronimo.toshop.domain.repository

import com.allysonjeronimo.toshop.domain.entity.Item

interface ItemRepository {

    suspend fun save(item: Item)

    suspend fun delete(id:Long)

    suspend fun deleteByShoppingList(shoppingListId:Long)

    suspend fun updatePurchasedByShoppingList(purchased:Boolean, shoppingListId: Long)

    suspend fun findByShoppingList(shoppingListId: Long) : List<Item>

    suspend fun countByShoppingList(shoppingListId: Long) : Int

    suspend fun count() : Int
}