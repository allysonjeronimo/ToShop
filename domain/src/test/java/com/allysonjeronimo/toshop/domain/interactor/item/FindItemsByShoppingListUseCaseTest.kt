package com.allysonjeronimo.toshop.domain.interactor.item

import com.allysonjeronimo.toshop.domain.interactor.item.data.ItemEntityFactory
import com.allysonjeronimo.toshop.domain.repository.ItemRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class FindItemsByShoppingListUseCaseTest {

    private val repository:ItemRepository = mockk()
    private val useCase:FindItemsByShoppingListUseCase = FindItemsByShoppingList(repository)
    private val dummyItemsList = ItemEntityFactory.dummyItemsList()

    companion object{
        const val SHOPPING_LIST_ID = 1L
    }

    @Test
    fun findItemsByShoppingList_onSuccessWithData_shouldReturnData() = runBlocking{
        // Given
        coEvery {
            repository.findByShoppingList(any())
        } returns dummyItemsList
        // When
        val list = useCase.execute(SHOPPING_LIST_ID)
        // Then
        coVerify {
            repository.findByShoppingList(SHOPPING_LIST_ID)
        }
        assertEquals(dummyItemsList.size, list.size)
    }

    @Test
    fun findItemsByShoppingList_onError_shouldNotReturnData() = runBlocking {
        // Given
        coEvery {
            repository.findByShoppingList(any())
        } throws Exception()
        // When
        val list = useCase.execute(SHOPPING_LIST_ID)
        // Then
        coVerify {
            repository.findByShoppingList(SHOPPING_LIST_ID)
        }
        assertEquals(0, list.size)
    }
}