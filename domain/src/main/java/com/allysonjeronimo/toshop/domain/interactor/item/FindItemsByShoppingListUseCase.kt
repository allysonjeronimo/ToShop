package com.allysonjeronimo.toshop.domain.interactor.item

import com.allysonjeronimo.toshop.domain.entity.Item
import com.allysonjeronimo.toshop.domain.repository.ItemRepository

class FindItemsByShoppingList(
    private val repository: ItemRepository
    ) : FindItemsByShoppingListUseCase{

    override suspend fun execute(shoppingListId:Long) : List<Item>{
        return repository.findByShoppingList(shoppingListId)
    }
}

interface FindItemsByShoppingListUseCase{
    suspend fun execute(shoppingListId: Long) : List<Item>
}