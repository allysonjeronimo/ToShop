package com.allysonjeronimo.toshop.data.repository

import com.allysonjeronimo.toshop.data.db.entity.CategoryEntity
import com.allysonjeronimo.toshop.data.db.entity.ProductEntity
import com.allysonjeronimo.toshop.data.db.entity.ProductNameEntity
import com.allysonjeronimo.toshop.domain.repository.CategoryRepository
import com.allysonjeronimo.toshop.domain.repository.ProductRepository
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class ProductRepositoryTest : BaseRepositoryTest(){

    private lateinit var productRepository: ProductRepository
    private lateinit var categoryRepository: CategoryRepository

    @Before
    fun setup(){
        productRepository = ProductRepositoryImpl(db.productDao())
        categoryRepository = CategoryRepositoryImpl(db.categoryDao())
        insertCategory()
    }

    private fun insertCategory() = testScope.runBlockingTest{
        val category = mockCategory()
        categoryRepository.insertAll(listOf(category))
    }

    private fun mockCategory() : CategoryEntity {
        return CategoryEntity(1, "")
    }

    private fun mockProducts() : List<ProductEntity>{
        val products = mutableListOf<ProductEntity>()
        for(i in 1..3){
            val product = ProductEntity(i.toLong(), 1)
            product.productNames = mutableListOf<ProductNameEntity>(
                ProductNameEntity(
                    locale= PRODUCT_LOCALE_PT_BR,
                    name="Product-$i-$PRODUCT_LOCALE_PT_BR",
                    productId = i.toLong()
                ),
                ProductNameEntity(
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

    @Test
    fun insert_default_products_on_create() = testScope.runBlockingTest{
        val productsPtBR = productRepository.search("", PRODUCT_LOCALE_PT_BR)

        assertTrue(productsPtBR.isNotEmpty())
    }

    companion object{
        const val PRODUCT_LOCALE_PT_BR = "pt-BR"
        const val PRODUCT_LOCALE_EN_US = "en-US"
    }

}