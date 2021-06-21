package com.allysonjeronimo.toshop.domain.interactor.item

import com.allysonjeronimo.toshop.domain.repository.ItemRepository

class DeleteItem(
    private val repository:ItemRepository
    ) : DeleteItemUseCase{

    override suspend fun execute(id:Long){
        repository.delete(id);
    }
}

interface DeleteItemUseCase{
    suspend fun execute(id:Long)
}