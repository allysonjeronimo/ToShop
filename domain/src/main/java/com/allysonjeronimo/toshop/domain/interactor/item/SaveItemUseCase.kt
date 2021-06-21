package com.allysonjeronimo.toshop.domain.interactor.item

import com.allysonjeronimo.toshop.domain.entity.Item
import com.allysonjeronimo.toshop.domain.repository.ItemRepository

class SaveItem(
    private val repository: ItemRepository
    ) : SaveItemUseCase {

    override suspend fun execute(item:Item){
        repository.save(item)
    }
}

interface SaveItemUseCase{
    suspend fun execute(item:Item)
}