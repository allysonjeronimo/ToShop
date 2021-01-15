package com.allysonjeronimo.toshop.model.db.repositories.interfaces

import com.allysonjeronimo.toshop.model.entities.ShoppingList

interface ShoppingListRepository {

    fun save(shoppingList: ShoppingList)

    fun delete(shoppingList: ShoppingList)

    fun deleteAll()

    fun find(id:Long) : ShoppingList?

    fun findAll() : kotlin.collections.List<ShoppingList>

    fun search(term:String) : kotlin.collections.List<ShoppingList>

    fun count() : Int
}