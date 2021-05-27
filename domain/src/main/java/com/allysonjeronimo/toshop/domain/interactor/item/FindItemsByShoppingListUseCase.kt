package com.allysonjeronimo.toshop.domain.interactor.item

import com.allysonjeronimo.toshop.domain.entity.Item
import com.allysonjeronimo.toshop.domain.repository.ItemRepository

class FindItemsByShoppingListUseCase(private val repository: ItemRepository) {

    suspend fun execute(shoppingListId:Long) : List<Item>{
        return repository.findByShoppingList(shoppingListId)
    }
}