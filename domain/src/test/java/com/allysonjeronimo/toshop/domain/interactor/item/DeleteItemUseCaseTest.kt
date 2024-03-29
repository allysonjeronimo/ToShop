package com.allysonjeronimo.toshop.domain.interactor.item

import com.allysonjeronimo.toshop.domain.repository.ItemRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class DeleteItemUseCaseTest {

    private val repository:ItemRepository = mockk()
    private val useCase:DeleteItemUseCase = DeleteItem(repository)

    companion object{
        const val ITEM_ID = 1L
    }

    @Test
    fun deleteItem_whenExecute_shouldCallDelete() = runBlocking{
        // Given
        coEvery {
            repository.delete(any())
        } returns Unit
        // When
        useCase.execute(ITEM_ID)
        // Then
        coVerify {
            repository.delete(ITEM_ID)
        }
    }

}