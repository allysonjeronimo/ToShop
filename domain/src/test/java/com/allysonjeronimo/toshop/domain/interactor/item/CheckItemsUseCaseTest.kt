package com.allysonjeronimo.toshop.domain.interactor.item

import com.allysonjeronimo.toshop.domain.repository.ItemRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class CheckItemsUseCaseTest {

    private val repository:ItemRepository = mockk()

    private val useCase:CheckItemsUseCase = CheckItems(repository)

    companion object{
        const val ITEM_CHECKED = true
        const val SHOPPING_LIST_ID = 1L
    }

    @Test
    fun checkItems_whenExecute_shouldCallUpdatePurchasedByShoppingList() = runBlocking{
        // Given
        coEvery {
            repository.updatePurchasedByShoppingList(ITEM_CHECKED, SHOPPING_LIST_ID)
        } returns Unit

        // When
        useCase.execute(ITEM_CHECKED, SHOPPING_LIST_ID)

        // Then
        coVerify {
            repository.updatePurchasedByShoppingList(ITEM_CHECKED, SHOPPING_LIST_ID)
        }
    }
}