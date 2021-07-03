package com.allysonjeronimo.toshop.domain.interactor.shoppinglist

import com.allysonjeronimo.toshop.domain.entity.ShoppingList
import com.allysonjeronimo.toshop.domain.interactor.shoppinglist.data.ShoppingListEntityFactory
import com.allysonjeronimo.toshop.domain.repository.ShoppingListRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SaveShoppingListUseCaseTest {

    private val repository:ShoppingListRepository = mockk()
    private val useCase:SaveShoppingListUseCase = SaveShoppingList(repository)
    private val dummyShoppingList = ShoppingListEntityFactory.dummyShoppingList()

    @Test
    fun saveShoppingList_whenExecute_shouldCallSave() = runBlocking{
        // Given
        coEvery {
            repository.save(any())
        } returns Unit

        // When
        useCase.execute(dummyShoppingList)

        // Then
        coVerify {
            repository.save(dummyShoppingList)
        }
    }

}