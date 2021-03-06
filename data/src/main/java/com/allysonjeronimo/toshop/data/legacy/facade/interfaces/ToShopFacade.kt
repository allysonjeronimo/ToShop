package com.allysonjeronimo.toshop.data.legacy.facade.interfaces

import com.allysonjeronimo.toshop.data.legacy.entities.Category
import com.allysonjeronimo.toshop.data.legacy.entities.Item
import com.allysonjeronimo.toshop.data.legacy.entities.Product
import com.allysonjeronimo.toshop.legacy.entities.ShoppingList

interface ToShopFacade {
    fun searchProducts(term:String) : List<Product>

    fun saveItem(item: Item)
    fun findItemsByShoppingList(shoppingListId: Long) : List<Item>
    fun deleteItem(item: Item)
    fun deleteItems(shoppingListId:Long)
    fun updateItemsPurchasedByShoppingList(purchased:Boolean, shoppingListId: Long)
    fun countItemsByShoppingList(shoppingListId: Long) : Int

    fun findShoppingList(id:Long) : ShoppingList?
    fun saveShoppingList(shoppingList: ShoppingList)
    fun deleteShoppingList(shoppingList: ShoppingList)
    fun deleteAllShoppingLists()
    fun countShoppingLists() : Int
    fun searchShoppingLists(term:String) : List<ShoppingList>
    fun findAllShoppingLists(): List<ShoppingList>

    fun findAllCategories() : List<Category>
}