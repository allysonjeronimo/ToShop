package com.allysonjeronimo.toshop.domain.interactor.shoppinglist

import com.allysonjeronimo.toshop.domain.repository.ShoppingListRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class DeleteShoppingListUseCaseTest {

    private val repository:ShoppingListRepository = mockk()
    private val useCase:DeleteShoppingListUseCase = DeleteShoppingList(repository)

    companion object{
        const val SHOPPING_LIST_ID = 1L
    }

    @Test
    fun deleteShoppingList_whenExecute_shouldCallDelete() = runBlocking{
        // Given
        coEvery {
            repository.delete(any())
        } returns Unit

        // When
        useCase.execute(SHOPPING_LIST_ID)

        // When
        coVerify {
            repository.delete(SHOPPING_LIST_ID)
        }
    }
}