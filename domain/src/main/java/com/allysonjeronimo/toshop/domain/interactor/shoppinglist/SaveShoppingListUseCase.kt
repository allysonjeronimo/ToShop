package com.allysonjeronimo.toshop.domain.interactor.shoppinglist

import com.allysonjeronimo.toshop.domain.entity.ShoppingList
import com.allysonjeronimo.toshop.domain.repository.ShoppingListRepository

class SaveShoppingList(
    private val repository: ShoppingListRepository
    ) : SaveShoppingListUseCase{

    override suspend fun execute(shoppingList:ShoppingList){
        repository.save(shoppingList)
    }
}

interface SaveShoppingListUseCase{
    suspend fun execute(shoppingList: ShoppingList)
}