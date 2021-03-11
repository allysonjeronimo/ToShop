package com.allysonjeronimo.toshop.data.mapper

import com.allysonjeronimo.toshop.data.db.entity.ShoppingListEntity
import com.allysonjeronimo.toshop.domain.entity.ShoppingList

internal class ShoppingListModelToEntityMapper : Mapper<ShoppingList, ShoppingListEntity> {

    override fun map(source: ShoppingList): ShoppingListEntity {
        return ShoppingListEntity(
            id = source.id,
            description = source.description,
            lastUpdate = source.lastUpdate
        )
    }
}