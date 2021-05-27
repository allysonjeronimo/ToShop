package com.allysonjeronimo.toshop.domain.interactor.shoppinglist

import com.allysonjeronimo.toshop.domain.entity.ShoppingList
import com.allysonjeronimo.toshop.domain.repository.ShoppingListRepository

class SearchShoppingListsUseCase(private val repository: ShoppingListRepository) {

    suspend fun execute(term:String):List<ShoppingList>{
        return repository.search(term)
    }
}