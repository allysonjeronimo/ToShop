package com.allysonjeronimo.toshop.data.repository

import com.allysonjeronimo.toshop.data.db.entity.Category
import com.allysonjeronimo.toshop.data.db.entity.CategoryName
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test


class CategoryRepositoryTest : BaseRepositoryTest(){

    private lateinit var categoryRepository: CategoryRepository

    @Before
    fun setup(){
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
        val countDefaultCategoriesPtBR = categoryRepository.count(CATEGORY_LOCALE_PT_BR)
        val countDefaultCategoriesEnUS = categoryRepository.count(CATEGORY_LOCALE_EN_US)
        val countInsertedCategories = categories.size
        // When
        categoryRepository.insertWithNames(categories)
        // Then
        var insertedCategories = categoryRepository.findAll(CATEGORY_LOCALE_PT_BR)
        assertEquals(countDefaultCategoriesPtBR + countInsertedCategories, insertedCategories.size)
        insertedCategories = categoryRepository.findAll(CATEGORY_LOCALE_EN_US)
        assertEquals(countDefaultCategoriesEnUS + countInsertedCategories, insertedCategories.size)
    }

    @Test
    fun insert_default_categories_on_create() = testScope.runBlockingTest{
        val categoriesPtBR = categoryRepository.findAll(CATEGORY_LOCALE_PT_BR)
        val categoriesEnUS = categoryRepository.findAll(CATEGORY_LOCALE_EN_US)

        assertTrue(categoriesPtBR.isNotEmpty())
        assertTrue(categoriesEnUS.isNotEmpty())
    }

    companion object{
        const val CATEGORY_LOCALE_PT_BR = "pt-BR"
        const val CATEGORY_LOCALE_EN_US = "en-US"
    }

}