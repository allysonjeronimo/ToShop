package com.allysonjeronimo.toshop.domain.interactor.shoppinglist

import com.allysonjeronimo.toshop.domain.entity.ShoppingList
import com.allysonjeronimo.toshop.domain.interactor.shoppinglist.data.ShoppingListEntityFactory
import com.allysonjeronimo.toshop.domain.repository.ShoppingListRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SearchShoppingListsUseCaseTest {

    private val repository:ShoppingListRepository = mockk()
    private val useCase:SearchShoppingListsUseCase = SearchShoppingLists(repository)
    private val dummyShoppingListsList = ShoppingListEntityFactory.dummyShoppingListList()

    companion object{
        const val SEARCH_TERM = ""
    }

    @Test
    fun searchShoppingLists_onSuccessWithData_shouldReturnData() = runBlocking{
        // Given
        coEvery {
            repository.search(any())
        } returns dummyShoppingListsList

        // When
        val list = useCase.execute(SEARCH_TERM)

        // Then
        assertEquals(dummyShoppingListsList.size, list.size)

        coVerify {
            repository.search(SEARCH_TERM)
        }
    }

    @Test
    fun searchShoppingLists_onError_shouldNotReturnData() = runBlocking{
        // Given
        coEvery {
            repository.search(any())
        } throws Exception()

        // When
        val list = useCase.execute(SEARCH_TERM)

        // Then
        assertEquals(0, list.size)

        coVerify {
            repository.search(SEARCH_TERM)
        }
    }
}