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
    fun deleteItemsByShoppingList_should_call_deleteByShoppingList() = runBlocking {
        coEvery {
            repository.deleteByShoppingList(any())
        } returns Unit

        useCase.execute(SHOPPING_LIST_ID)

        coVerify {
            repository.deleteByShoppingList(SHOPPING_LIST_ID)
        }
    }
}