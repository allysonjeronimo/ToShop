package com.allysonjeronimo.toshop.domain.interactor.shoppinglist

import com.allysonjeronimo.toshop.domain.repository.ShoppingListRepository

class CountShoppingList(
    private val repository: ShoppingListRepository
    ) : CountShoppingListUseCase{

    override suspend fun execute() : Int{
        return repository.count()
    }
}

interface CountShoppingListUseCase{
    suspend fun execute() : Int
}