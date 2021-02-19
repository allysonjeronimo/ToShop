package com.allysonjeronimo.toshop.legacy.repositories

import com.allysonjeronimo.toshop.legacy.entities.ShoppingList

interface ShoppingListRepository {

    fun save(shoppingList: ShoppingList)

    fun delete(shoppingList: ShoppingList)

    fun deleteAll()

    fun find(id:Long) : ShoppingList?

    fun findAll() : kotlin.collections.List<ShoppingList>

    fun search(term:String) : kotlin.collections.List<ShoppingList>

    fun count() : Int
}