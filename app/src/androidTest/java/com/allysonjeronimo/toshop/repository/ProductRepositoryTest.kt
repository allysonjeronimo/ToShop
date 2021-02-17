package com.allysonjeronimo.toshop.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.allysonjeronimo.toshop.data.AppDatabase
import com.allysonjeronimo.toshop.data.entity.Category
import com.allysonjeronimo.toshop.data.entity.Product
import com.allysonjeronimo.toshop.data.entity.ProductName
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ProductRepositoryTest {

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()
    private lateinit var productRepository: ProductRepository
    private lateinit var categoryRepository: CategoryRepository

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setup(){
        val db:AppDatabase = mockDatabase()
        productRepository = ProductDataSource(db.productDao())
        categoryRepository = CategoryDataSource(db.categoryDao())
        insertCategory()
    }

    private fun insertCategory() = testScope.runBlockingTest{
        val category = mockCategory()
        categoryRepository.insertAll(listOf(category))
    }

    private fun mockDatabase(): AppDatabase {
        return Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AppDatabase::class.java)
            .setTransactionExecutor(testDispatcher.asExecutor())
            .setQueryExecutor(testDispatcher.asExecutor())
            .build()
    }

    private fun mockCategory() : Category{
        return Category(1, "")
    }

    private fun mockProducts() : List<Product>{
        val products = mutableListOf<Product>()
        for(i in 1..3){
            val product = Product(i.toLong(), 1)
            product.productNames = mutableListOf<ProductName>(
                ProductName(
                    locale= PRODUCT_LOCALE_PT_BR,
                    name="Product-$i-$PRODUCT_LOCALE_PT_BR",
                    productId = i.toLong()
                ),
                ProductName(
                    locale = PRODUCT_LOCALE_EN_US,
                    name="Product-$i-$PRODUCT_LOCALE_EN_US",
                    productId = i.toLong()
                )
            )
            products.add(product)
        }
        return products
    }

    @Test
    fun insert_products_with_different_location_names() = testScope.runBlockingTest{

        val products = mockProducts()

        productRepository.insertWithNames(products)

        var insertedProducts = productRepository.search("Product", PRODUCT_LOCALE_PT_BR)
        assertEquals(products.size, insertedProducts.size)
        insertedProducts = productRepository.search("Product", PRODUCT_LOCALE_EN_US)
        assertEquals(products.size, insertedProducts.size)
    }

    companion object{
        const val PRODUCT_LOCALE_PT_BR = "pt-BR"
        const val PRODUCT_LOCALE_EN_US = "en-US"
    }

}