package com.allysonjeronimo.toshop.domain.interactor.item

import com.allysonjeronimo.toshop.domain.repository.ItemRepository

class CountItemsByShoppingList(
    private val repository: ItemRepository
    ) : CountItemsByShoppingListUseCase{

    override suspend fun execute(shoppingListId:Long) : Int{
        return repository.countByShoppingList(shoppingListId)
    }
}

interface CountItemsByShoppingListUseCase{
    suspend fun execute(shoppingListId: Long) : Int
}