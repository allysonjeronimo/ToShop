package com.allysonjeronimo.toshop.data.mapper

import com.allysonjeronimo.toshop.data.db.entity.CategoryWithName
import com.allysonjeronimo.toshop.domain.entity.Category

internal class CategoryEntityToModelMapper : Mapper<CategoryWithName, Category>{

    override fun map(source: CategoryWithName): Category {
        return Category(
            id = source.id,
            resourceIconName = source.resourceIconName,
            name = source.name
        )
    }

}