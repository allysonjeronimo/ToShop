package com.allysonjeronimo.toshop.domain.interactor.item

import com.allysonjeronimo.toshop.domain.repository.ItemRepository

class CountItemsByShoppingListUseCase(private val repository: ItemRepository) {

    suspend fun execute(shoppingListId:Long) : Int{
        return repository.countByShoppingList(shoppingListId)
    }
}