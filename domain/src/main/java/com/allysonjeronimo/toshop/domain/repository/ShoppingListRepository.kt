package com.allysonjeronimo.toshop.domain.repository

import com.allysonjeronimo.toshop.domain.entity.ShoppingList

interface ShoppingListRepository {

    suspend fun save(shoppingList: ShoppingList)

    suspend fun delete(id:Long)

    suspend fun deleteAll()

    suspend fun search(term:String) : List<ShoppingList>

    suspend fun count() : Int

}