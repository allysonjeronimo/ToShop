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
    fun searchProductsUseCase_should_call_search() = runBlocking{
        coEvery {
            repository.search(SEARCH_TERM, LOCALE)
        } returns dummyProductList

        val list = useCase.execute(SEARCH_TERM, LOCALE)

        assertEquals(list, dummyProductList)

        coVerify {
            repository.search(SEARCH_TERM, LOCALE)
        }
    }

}