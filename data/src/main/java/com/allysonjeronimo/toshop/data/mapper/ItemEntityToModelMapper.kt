package com.allysonjeronimo.toshop.data.mapper

import com.allysonjeronimo.toshop.data.db.entity.ItemWithCategoryIcon
import com.allysonjeronimo.toshop.domain.entity.Item

internal class ItemEntityToModelMapper : Mapper<ItemWithCategoryIcon, Item>{

    override fun map(source: ItemWithCategoryIcon): Item {
        return Item(
            id = source.id,
            description = source.description,
            quantity = source.quantity,
            unit = source.unit,
            price = source.price,
            notes = source.notes,
            purchased = source.purchased,
            lastUpdate = source.lastUpdate,
            categoryId = source.categoryId,
            listId = source.listId,
            categoryResourceIcon = source.categoryResourceIcon
        )
    }

}