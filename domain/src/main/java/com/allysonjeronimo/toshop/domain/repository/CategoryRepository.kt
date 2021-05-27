package com.allysonjeronimo.toshop.domain.repository

import com.allysonjeronimo.toshop.domain.entity.Category

interface CategoryRepository {

    suspend fun findAll(locale:String) : List<Category>

    suspend fun count(locale:String) : Int
}