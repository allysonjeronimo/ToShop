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
    fun countShoppingList_should_call_count() = runBlocking{
        coEvery {
            repository.count()
        } returns COUNT

        val count = useCase.execute()

        assertEquals(count, COUNT)

        coVerify {
            repository.count()
        }
    }
}