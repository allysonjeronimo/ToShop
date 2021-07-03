package com.allysonjeronimo.toshop.domain.interactor.shoppinglist

import com.allysonjeronimo.toshop.domain.entity.ShoppingList
import com.allysonjeronimo.toshop.domain.repository.ShoppingListRepository
import java.lang.Exception

class SearchShoppingLists(
    private val repository: ShoppingListRepository
    ) : SearchShoppingListsUseCase{

    override suspend fun execute(term:String):List<ShoppingList> = try{
        repository.search(term)
    }catch(ex:Exception){
        listOf()
    }
}

interface SearchShoppingListsUseCase{
    suspend fun execute(term:String) : List<ShoppingList>
}