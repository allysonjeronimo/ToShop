package com.allysonjeronimo.toshop.legacy.repositories

import com.allysonjeronimo.toshop.data.legacy.entities.Category

interface CategoryRepository {
    fun findAll() : List<Category>
}