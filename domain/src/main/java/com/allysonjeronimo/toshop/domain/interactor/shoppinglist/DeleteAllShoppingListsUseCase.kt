package com.allysonjeronimo.toshop.domain.interactor.shoppinglist

import com.allysonjeronimo.toshop.domain.repository.ShoppingListRepository

class DeleteAllShoppingLists(
    private val repository:ShoppingListRepository
    ) : DeleteAllShoppingListsUseCase {

    override suspend fun execute(){
        repository.deleteAll()
    }
}

interface DeleteAllShoppingListsUseCase{
    suspend fun execute()
}