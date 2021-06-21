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
    fun findAllCategories_return_list_with_success() = runBlocking{
        // Given
        coEvery {
            repository.findAll("")
        } returns dummyCategoryList

        // When
        val list = useCase.execute("")

        // Then
        assertEquals(list.size, dummyCategoryList.size)
    }

    @Test
    fun findAllCategories_return_exception() = runBlocking {
        coEvery {
            repository.findAll("")
        } throws Exception()

        val list = useCase.execute("")

        assertEquals(list.size, 0)
    }

}