package com.allysonjeronimo.toshop.domain.interactor.shoppinglist

import com.allysonjeronimo.toshop.domain.entity.ShoppingList
import com.allysonjeronimo.toshop.domain.repository.ShoppingListRepository

class SaveShoppingListUseCase(private val repository: ShoppingListRepository) {

    suspend fun execute(shoppingList:ShoppingList){
        repository.save(shoppingList)
    }
}