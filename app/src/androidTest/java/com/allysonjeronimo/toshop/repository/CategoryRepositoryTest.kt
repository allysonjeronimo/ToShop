package com.allysonjeronimo.toshop.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.allysonjeronimo.toshop.data.AppDatabase
import com.allysonjeronimo.toshop.data.entity.Category
import com.allysonjeronimo.toshop.data.entity.CategoryName
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

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
            for(j in 1..2){
                names.add(CategoryName(j.toLong(), "", "$i-$j", i.toLong()))
            }
            category.categoryNames = names
        }
        return categories
    }

    private fun mockDatabase() : AppDatabase{
        return Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            AppDatabase::class.java)
            .setTransactionExecutor(testDispatcher.asExecutor())
            .setQueryExecutor(testDispatcher.asExecutor())
            .build()
    }

    @Test
    fun `insert_categories_with_names`(){
        // Given, When, Then
        testScope.runBlockingTest{
            categories = mockCategories()
            categoryRepository.insertWithNames(categories)
        }

    }


}