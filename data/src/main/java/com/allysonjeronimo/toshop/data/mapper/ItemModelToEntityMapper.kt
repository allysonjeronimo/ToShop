package com.allysonjeronimo.toshop.data.mapper

import com.allysonjeronimo.toshop.data.db.entity.ItemEntity
import com.allysonjeronimo.toshop.domain.entity.Item

internal class ItemModelToEntityMapper : Mapper<Item, ItemEntity>{

    override fun map(source: Item): ItemEntity {
        return ItemEntity(
            id = source.id,
            description = source.description,
            quantity = source.quantity,
            unit = source.unit,
            price = source.price,
            notes = source.notes,
            purchased = source.purchased,
            lastUpdate = source.lastUpdate,
            categoryId = source.categoryId,
            listId = source.listId
        )
    }
}