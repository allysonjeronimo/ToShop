package com.allysonjeronimo.toshop.domain.interactor.category

import com.allysonjeronimo.toshop.domain.interactor.category.data.CategoryEntityFactory
import com.allysonjeronimo.toshop.domain.repository.CategoryRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class FindAllCategoriesUseCaseTest {

    private val repository: CategoryRepository = mockk()

    private val useCase:FindAllCategoriesUseCase = FindAllCategories(repository)

    private val dummyCategoryList = CategoryEntityFactory.dummyCategoryList()

    @Test
    fun findAllCategories_onSuccessWithData_shouldReturnData() = runBlocking{
        // Given
        coEvery {
            repository.findAll("")
        } returns dummyCategoryList

        // When
        val list = useCase.execute("")

        // Then
        assertEquals(dummyCategoryList.size, list.size)
    }

    @Test
    fun findAllCategories_onError_shouldNotReturnData() = runBlocking {
        // Given
        coEvery {
            repository.findAll("")
        } throws Exception()
        // When
        val list = useCase.execute("")
        // Then
        assertEquals(0, list.size)
    }

}