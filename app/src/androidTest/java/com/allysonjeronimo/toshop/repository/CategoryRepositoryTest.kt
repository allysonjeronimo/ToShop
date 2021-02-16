package com.allysonjeronimo.toshop.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.allysonjeronimo.toshop.data.AppDatabase
import com.allysonjeronimo.toshop.data.entity.Category
import com.allysonjeronimo.toshop.data.entity.CategoryName
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
class CategoryRepositoryTest {

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()
    private lateinit var categoryRepository: CategoryRepository
    private lateinit var categories:List<Category>

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setup(){
        val db = mockDatabase()
        categoryRepository = CategoryDataSource(db.categoryDao())
    }

    private fun mockCategories() : List<Category>{
        val categories = mutableListOf<Category>()
        for(i in 1..3){
            val category = Category(i.toLong(), "")
            val names = mutableListOf<CategoryName>()
            names.add(CategoryName(locale = CATEGORY_LOCALE_PT_BR, name = "Category$i-$CATEGORY_LOCALE_PT_BR", categoryId = i.toLong()))
            names.add(CategoryName(locale = CATEGORY_LOCALE_EN_US, name = "Category$i-$CATEGORY_LOCALE_EN_US", categoryId = i.toLong()))
            category.categoryNames = names
            categories.add(category)
        }
        return categories
    }

    private fun mockDatabase() : AppDatabase{
        return Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AppDatabase::class.java)
            .setTransactionExecutor(testDispatcher.asExecutor())
            .setQueryExecutor(testDispatcher.asExecutor())
            .build()
    }

    @Test
    fun `insert_categories_with_different_location_names`() = testScope.runBlockingTest{
        // Given
        categories = mockCategories()
        // When
        categoryRepository.insertWithNames(categories)
        // Then
        var insertedCategories = categoryRepository.findAll(CATEGORY_LOCALE_PT_BR)
        assertEquals(categories.size, insertedCategories.size)
        insertedCategories = categoryRepository.findAll(CATEGORY_LOCALE_EN_US)
        assertEquals(categories.size, insertedCategories.size)
    }

    companion object{
        const val CATEGORY_LOCALE_PT_BR = "pt-BR"
        const val CATEGORY_LOCALE_EN_US = "en-US"
    }



}