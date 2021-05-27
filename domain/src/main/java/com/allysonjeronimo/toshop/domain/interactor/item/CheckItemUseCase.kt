package com.allysonjeronimo.toshop.domain.interactor.item

import com.allysonjeronimo.toshop.domain.repository.ItemRepository

class CheckItemUseCase(private val repository: ItemRepository) {

    suspend fun execute(checked:Boolean, shoppingListId:Long){
        repository.updatePurchasedByShoppingList(checked, shoppingListId)
    }
}