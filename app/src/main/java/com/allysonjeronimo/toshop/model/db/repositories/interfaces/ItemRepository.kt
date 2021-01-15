package com.allysonjeronimo.toshop.model.db.repositories.interfaces

import com.allysonjeronimo.toshop.model.entities.Item

interface ItemRepository {

    fun save(item: Item)

    fun delete(item: Item)

    fun deleteByShoppingList(shoppingListId: Long)

    fun updatePurchasedByShoppingList(purchased:Boolean, shoppingListId: Long)

    fun find(id:Long):Item?

    fun findByShoppingList(shoppingListId:Long) : List<Item>

    fun countByShoppingList(shoppingListId: Long) : Int

}