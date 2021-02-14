package com.allysonjeronimo.toshop.repository

import com.allysonjeronimo.toshop.data.entity.Category

interface CategoryRepository {

    suspend fun findAll(locale:String) : List<Category>

}