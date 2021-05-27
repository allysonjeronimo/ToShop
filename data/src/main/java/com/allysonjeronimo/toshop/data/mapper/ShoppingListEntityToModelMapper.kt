package com.allysonjeronimo.toshop.data.mapper

import com.allysonjeronimo.toshop.data.db.entity.ShoppingListWithDetails
import com.allysonjeronimo.toshop.domain.entity.ShoppingList

internal class ShoppingListEntityToModelMapper : Mapper<ShoppingListWithDetails, ShoppingList>{

    override fun map(source: ShoppingListWithDetails): ShoppingList {
        return ShoppingList(
            id = source.id,
            description = source.description,
            lastUpdate = source.lastUpdate,
            quantityPurchasedItems = source.quantityPurchasedItems,
            quantityItems = source.quantityItems,
            total = source.total
        )
    }
}