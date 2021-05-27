package com.allysonjeronimo.toshop.domain.interactor.shoppinglist

import com.allysonjeronimo.toshop.domain.repository.ShoppingListRepository

class CountShoppingListUseCase(private val repository: ShoppingListRepository) {

    suspend fun execute() : Int{
        return repository.count()
    }
}