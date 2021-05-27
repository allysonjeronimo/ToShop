package com.allysonjeronimo.toshop.domain.interactor.shoppinglist

import com.allysonjeronimo.toshop.domain.repository.ShoppingListRepository

class DeleteAllShoppingListsUseCase(private val repository:ShoppingListRepository) {

    suspend fun execute(){
        repository.deleteAll()
    }

}