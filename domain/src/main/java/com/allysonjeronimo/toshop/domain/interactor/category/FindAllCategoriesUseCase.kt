package com.allysonjeronimo.toshop.domain.interactor.category

import com.allysonjeronimo.toshop.domain.entity.Category
import com.allysonjeronimo.toshop.domain.repository.CategoryRepository

class FindAllCategoriesUseCase(private val repository: CategoryRepository) {

    suspend fun execute(locale:String) : List<Category> {
        return repository.findAll(locale)
    }
}