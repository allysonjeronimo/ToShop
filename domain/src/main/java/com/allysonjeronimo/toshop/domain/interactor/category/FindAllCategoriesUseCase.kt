package com.allysonjeronimo.toshop.domain.interactor.category

import com.allysonjeronimo.toshop.domain.entity.Category
import com.allysonjeronimo.toshop.domain.repository.CategoryRepository

class FindAllCategories(
    private val repository: CategoryRepository
    ) : FindAllCategoriesUseCase {

    override suspend fun execute(locale:String) : List<Category> = try {
        repository.findAll(locale)
    } catch(ex:Exception){
        listOf()
    }
}

interface FindAllCategoriesUseCase{
    suspend fun execute(locale:String) : List<Category>
}