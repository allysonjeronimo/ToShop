package com.allysonjeronimo.toshop.domain.interactor.item

import com.allysonjeronimo.toshop.domain.repository.ItemRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class CountItemsByShoppingListUseCaseTest {

    private val repository: ItemRepository = mockk()

    private val useCase: CountItemsByShoppingListUseCase = CountItemsByShoppingList(repository)

    companion object{
        const val SHOPPING_LIST_ID = 1L
        const val COUNT = 1
    }

    @Test
    fun countItemsByShoppingList_whenExecute_shouldCallCountByShoppingList() = runBlocking {
        // Given
        coEvery {
            repository.countByShoppingList(SHOPPING_LIST_ID)
        } returns COUNT

        // When
        val count = useCase.execute(SHOPPING_LIST_ID)

        // Then
        coVerify {
            repository.countByShoppingList(SHOPPING_LIST_ID)
        }

        assertEquals(count, COUNT)
    }
}