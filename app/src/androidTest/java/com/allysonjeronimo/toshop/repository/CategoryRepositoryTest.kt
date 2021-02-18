package com.allysonjeronimo.toshop.repository

import com.allysonjeronimo.toshop.data.entity.Category
import com.allysonjeronimo.toshop.data.entity.CategoryName
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test


class CategoryRepositoryTest : BaseRepositoryTest(){

    private lateinit var categoryRepository: CategoryRepository

    @Before
    fun setup(){
        val db = mockDatabase()
        categoryRepository = CategoryDataSource(db.categoryDao())
    }

    private fun mockCategories() : List<Category>{
        val categories = mutableListOf<Category>()
        for(i in 1..3){
            val category = Category(i.toLong(), "")
            category.categoryNames = mutableListOf(
                CategoryName(
                    locale = CATEGORY_LOCALE_PT_BR,
                    name = "Category$i-$CATEGORY_LOCALE_PT_BR",
                    categoryId = i.toLong()
                ),
                CategoryName(
                    locale = CATEGORY_LOCALE_EN_US,
                    name = "Category$i-$CATEGORY_LOCALE_EN_US",
                    categoryId = i.toLong()
                )
            )
            categories.add(category)
        }
        return categories
    }

    @Test
    fun insert_categories_with_different_location_names() = testScope.runBlockingTest{
        // Given
        var categories = mockCategories()
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