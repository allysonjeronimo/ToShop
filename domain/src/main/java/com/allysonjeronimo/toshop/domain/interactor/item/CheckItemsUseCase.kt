package com.allysonjeronimo.toshop.domain.interactor.item

import com.allysonjeronimo.toshop.domain.repository.ItemRepository

class CheckItems(
    private val repository: ItemRepository
    ) : CheckItemsUseCase {

    override suspend fun execute(checked:Boolean, shoppingListId:Long){
        repository.updatePurchasedByShoppingList(checked, shoppingListId)
    }
}

interface CheckItemsUseCase{
    suspend fun execute(checked: Boolean, shoppingListId: Long)
}