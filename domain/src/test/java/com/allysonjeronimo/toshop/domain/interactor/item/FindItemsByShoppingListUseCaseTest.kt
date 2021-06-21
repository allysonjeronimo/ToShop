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
    fun findItemsByShoppingList_should_call_() = runBlocking{
        coEvery {
            repository.findByShoppingList(any())
        } returns dummyItemsList

        val list = useCase.execute(SHOPPING_LIST_ID)

        coVerify {
            repository.findByShoppingList(SHOPPING_LIST_ID)
        }

        assertEquals(list, dummyItemsList)
    }
}