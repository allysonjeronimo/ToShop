package com.allysonjeronimo.toshop.data.legacy.facade

import android.content.Context
import com.allysonjeronimo.toshop.data.legacy.repositories.CategoryRepositoryImpl
import com.allysonjeronimo.toshop.data.legacy.repositories.ItemRepositoryImpl
import com.allysonjeronimo.toshop.data.legacy.repositories.ProductRepositoryImpl
import com.allysonjeronimo.toshop.data.legacy.repositories.ShoppingListRepositoryImpl
import com.allysonjeronimo.toshop.data.legacy.entities.Category
import com.allysonjeronimo.toshop.data.legacy.entities.Item
import com.allysonjeronimo.toshop.data.legacy.entities.Product
import com.allysonjeronimo.toshop.legacy.entities.ShoppingList
import com.allysonjeronimo.toshop.data.legacy.facade.interfaces.ToShopFacade

class ToShopFacadeImpl(context: Context) : ToShopFacade {

    private val categoryRepository = CategoryRepositoryImpl(context)
    private val itemRepository = ItemRepositoryImpl(context)
    private val productRepository = ProductRepositoryImpl(context)
    private val shoppingListRepository = ShoppingListRepositoryImpl(context)

    override fun searchProducts(term: String): List<Product> {
        return productRepository.search(term)
    }

    override fun saveItem(item: Item) {
        itemRepository.save(item)
    }

    override fun findItemsByShoppingList(shoppingListId: Long): List<Item> {
        return itemRepository.findByShoppingList(shoppingListId)
    }

    override fun deleteItem(item: Item) {
        itemRepository.delete(item)
    }

    override fun deleteItems(shoppingListId: Long) {
        itemRepository.deleteByShoppingList(shoppingListId)
    }

    override fun updateItemsPurchasedByShoppingList(purchased:Boolean, shoppingListId: Long) {
        itemRepository.updatePurchasedByShoppingList(purchased, shoppingListId)
    }

    override fun countItemsByShoppingList(shoppingListId: Long): Int {
        return itemRepository.countByShoppingList(shoppingListId)
    }

    override fun findShoppingList(id: Long): ShoppingList? {
        return shoppingListRepository.find(id)
    }

    override fun saveShoppingList(shoppingList: ShoppingList) {
        shoppingListRepository.save(shoppingList)
    }

    override fun deleteShoppingList(shoppingList: ShoppingList) {
        shoppingListRepository.delete(shoppingList)
    }

    override fun deleteAllShoppingLists() {
        shoppingListRepository.deleteAll()
    }

    override fun countShoppingLists(): Int {
        return shoppingListRepository.count()
    }

    override fun searchShoppingLists(term: String): List<ShoppingList> {
        return shoppingListRepository.search(term)
    }

    override fun findAllShoppingLists(): List<ShoppingList> {
        return shoppingListRepository.findAll()
    }

    override fun findAllCategories(): List<Category> {
        return categoryRepository.findAll()
    }
}