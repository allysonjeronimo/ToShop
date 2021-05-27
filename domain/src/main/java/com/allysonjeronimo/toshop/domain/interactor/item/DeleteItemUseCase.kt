package com.allysonjeronimo.toshop.domain.interactor.item

import com.allysonjeronimo.toshop.domain.repository.ItemRepository

class DeleteItemUseCase(private val repository:ItemRepository) {

    suspend fun execute(id:Long){
        repository.delete(id);
    }
}