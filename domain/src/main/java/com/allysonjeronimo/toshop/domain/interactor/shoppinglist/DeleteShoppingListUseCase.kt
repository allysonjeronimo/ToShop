package com.allysonjeronimo.toshop.domain.interactor.shoppinglist

import com.allysonjeronimo.toshop.domain.repository.ShoppingListRepository

class DeleteShoppingListUseCase(private val repository: ShoppingListRepository) {

    suspend fun execute(id:Long){
        repository.delete(id)
    }
}