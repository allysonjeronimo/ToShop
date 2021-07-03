package com.allysonjeronimo.toshop.domain.interactor.product

import com.allysonjeronimo.toshop.domain.interactor.product.data.ProductEntityFactory
import com.allysonjeronimo.toshop.domain.repository.ProductRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SearchProductsUseCaseTest {

    private val repository:ProductRepository = mockk()
    private val useCase:SearchProductsUseCase = SearchProducts(repository)
    private val dummyProductList = ProductEntityFactory.dummyProductList()

    companion object{
        const val SEARCH_TERM = "P"
        const val LOCALE = "pt-BR"
    }

    @Test
    fun searchProducts_onSuccessWithData_shouldReturnData() = runBlocking{
        // Given
        coEvery {
            repository.search(SEARCH_TERM, LOCALE)
        } returns dummyProductList

        // When
        val list = useCase.execute(SEARCH_TERM, LOCALE)

        // Then
        assertEquals(dummyProductList.size, list.size)
    }

    @Test
    fun searchProducts_onError_shouldNotReturnData() = runBlocking{
        // Given
        coEvery {
            repository.search(SEARCH_TERM, LOCALE)
        } throws Exception()

        // When
        val list = useCase.execute(SEARCH_TERM, LOCALE)

        // Then
        assertEquals(0, list.size)
    }

}