package com.allysonjeronimo.toshop.domain.interactor.shoppinglist

import com.allysonjeronimo.toshop.domain.repository.ShoppingListRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class CountShoppingListUseCaseTest {

    private val repository:ShoppingListRepository = mockk()
    private val useCase:CountShoppingListUseCase = CountShoppingList(repository)

    companion object{
        const val COUNT = 1
    }

    @Test
    fun countShoppingList_whenExecute_shouldCallCount() = runBlocking{
        // Given
        coEvery {
            repository.count()
        } returns COUNT

        // When
        val count = useCase.execute()

        // Then
        assertEquals(count, COUNT)

        coVerify {
            repository.count()
        }
    }
}