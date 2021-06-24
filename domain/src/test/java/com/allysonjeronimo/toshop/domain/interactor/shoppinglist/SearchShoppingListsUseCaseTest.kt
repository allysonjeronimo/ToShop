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
    fun searchShoppingLists_should_call_search() = runBlocking{
        coEvery {
            repository.search(any())
        } returns dummyShoppingListsList

        val list = useCase.execute(SEARCH_TERM)

        assertEquals(list, dummyShoppingListsList)

        coVerify {
            repository.search(SEARCH_TERM)
        }
    }
}