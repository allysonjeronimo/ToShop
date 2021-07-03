package com.allysonjeronimo.toshop.domain.interactor.item

import com.allysonjeronimo.toshop.domain.interactor.item.data.ItemEntityFactory
import com.allysonjeronimo.toshop.domain.repository.ItemRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SaveItemUseCaseTest {

    private val repository:ItemRepository = mockk()
    private val useCase:SaveItemUseCase = SaveItem(repository)
    private val dummyItem = ItemEntityFactory.dummyItem()

    @Test
    fun saveItem_whenExecute_shouldCallSave() = runBlocking{
        // Given
        coEvery {
            repository.save(any())
        } returns Unit
        // When
        useCase.execute(dummyItem)
        // Then
        coVerify {
            repository.save(dummyItem)
        }
    }
}