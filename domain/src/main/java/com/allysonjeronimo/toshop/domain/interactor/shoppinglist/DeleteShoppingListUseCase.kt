package com.allysonjeronimo.toshop.domain.interactor.shoppinglist

import com.allysonjeronimo.toshop.domain.repository.ShoppingListRepository

class DeleteShoppingList(
    private val repository: ShoppingListRepository
    ) : DeleteShoppingListUseCase{

    override suspend fun execute(id:Long){
        repository.delete(id)
    }
}

interface DeleteShoppingListUseCase{
    suspend fun execute(id:Long)
}