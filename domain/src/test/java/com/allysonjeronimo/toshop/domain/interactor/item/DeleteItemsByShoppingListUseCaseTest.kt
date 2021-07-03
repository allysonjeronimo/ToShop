package com.allysonjeronimo.toshop.domain.interactor.item

import com.allysonjeronimo.toshop.domain.repository.ItemRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class DeleteItemsByShoppingListUseCaseTest {

    private val repository: ItemRepository = mockk()
    private val useCase:DeleteItemsByShoppingListUseCase = DeleteItemsByShoppingList(repository)

    companion object{
        const val SHOPPING_LIST_ID = 1L
    }

    @Test
    fun deleteItemsByShoppingList_whenExecute_shouldCallDeleteByShoppingList() = runBlocking {
        // Given
        coEvery {
            repository.deleteByShoppingList(any())
        } returns Unit
        // When
        useCase.execute(SHOPPING_LIST_ID)
        // Then
        coVerify {
            repository.deleteByShoppingList(SHOPPING_LIST_ID)
        }
    }
}