package com.allysonjeronimo.toshop.domain.interactor.item

import com.allysonjeronimo.toshop.domain.repository.ItemRepository

class DeleteItemByShoppingListUseCase(private val repository: ItemRepository) {

    suspend fun execute(shoppingListId:Long){
        repository.deleteByShoppingList(shoppingListId)
    }
}