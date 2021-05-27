package com.allysonjeronimo.toshop.domain.interactor.item

import com.allysonjeronimo.toshop.domain.entity.Item
import com.allysonjeronimo.toshop.domain.repository.ItemRepository

class SaveItemUseCase(private val repository: ItemRepository) {

    suspend fun execute(item:Item){
        repository.save(item)
    }
}