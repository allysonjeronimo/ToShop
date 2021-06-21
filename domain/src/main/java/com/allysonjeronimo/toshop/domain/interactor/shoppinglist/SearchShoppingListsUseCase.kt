package com.allysonjeronimo.toshop.domain.interactor.shoppinglist

import com.allysonjeronimo.toshop.domain.entity.ShoppingList
import com.allysonjeronimo.toshop.domain.repository.ShoppingListRepository

class SearchShoppingLists(
    private val repository: ShoppingListRepository
    ) : SearchShoppingListsUseCase{

    override suspend fun execute(term:String):List<ShoppingList>{
        return repository.search(term)
    }
}

interface SearchShoppingListsUseCase{
    suspend fun execute(term:String) : List<ShoppingList>
}