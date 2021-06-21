package com.allysonjeronimo.toshop.domain.interactor.item

import com.allysonjeronimo.toshop.domain.repository.ItemRepository

class DeleteItemsByShoppingList(
    private val repository: ItemRepository
    ) : DeleteItemsByShoppingListUseCase{

    override suspend fun execute(shoppingListId:Long){
        repository.deleteByShoppingList(shoppingListId)
    }
}

interface DeleteItemsByShoppingListUseCase{
    suspend fun execute(shoppingListId: Long)
}