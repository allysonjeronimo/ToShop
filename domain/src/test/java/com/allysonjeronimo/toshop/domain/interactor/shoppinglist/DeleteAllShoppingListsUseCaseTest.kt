package com.allysonjeronimo.toshop.domain.interactor.shoppinglist

import com.allysonjeronimo.toshop.domain.repository.ShoppingListRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class DeleteAllShoppingListsUseCaseTest {

    private val repository:ShoppingListRepository = mockk()
    private val useCase:DeleteAllShoppingListsUseCase = DeleteAllShoppingLists(repository)

    @Test
    fun deleteAllShoppingLists_whenExecute_shouldCallDeleteAll() = runBlocking{
        // Given
        coEvery {
            repository.deleteAll()
        } returns Unit

        // When
        useCase.execute()

        // Then
        coVerify {
            repository.deleteAll()
        }
    }

}