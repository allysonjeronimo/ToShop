package com.allysonjeronimo.toshop.model.db.repositories.interfaces

import com.allysonjeronimo.toshop.model.entities.Category

interface CategoryRepository {
    fun findAll() : List<Category>
}