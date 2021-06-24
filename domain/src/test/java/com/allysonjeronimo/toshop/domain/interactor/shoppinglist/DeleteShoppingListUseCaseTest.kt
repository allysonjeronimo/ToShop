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
    fun deleteShoppingList_should_call_delete() = runBlocking{
        coEvery {
            repository.delete(any())
        } returns Unit

        useCase.execute(SHOPPING_LIST_ID)

        coVerify {
            repository.delete(SHOPPING_LIST_ID)
        }
    }
}