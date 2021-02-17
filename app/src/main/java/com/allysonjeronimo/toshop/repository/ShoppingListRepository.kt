package com.allysonjeronimo.toshop.repository

import com.allysonjeronimo.toshop.data.entity.ShoppingList
import com.allysonjeronimo.toshop.data.entity.ShoppingListWithDetails

interface ShoppingListRepository {

    suspend fun save(shoppingList:ShoppingList)

    suspend fun delete(id:Long)

    suspend fun deleteAll()

    suspend fun search(term:String) : List<ShoppingListWithDetails>

    suspend fun count() : Int

}