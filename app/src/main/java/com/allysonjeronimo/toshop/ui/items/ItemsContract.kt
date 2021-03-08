package com.allysonjeronimo.toshop.ui.items

import com.allysonjeronimo.toshop.legacy.entities.Item
import com.allysonjeronimo.toshop.legacy.entities.Product

interface ItemsContract {
    interface ItemsPresenter{
        fun searchProducts(term:String) : List<Product>
        fun checkItem(item: Item)
        fun loadItems(shoppingListId:Long)
        fun deleteItem(item: Item)
        fun saveItem(item: Item) : Boolean
        fun deleteShoppingList(shoppingListId: Long)
        fun deleteAll(shoppingListId:Long)
        fun checkAll(shoppingListId:Long)
        fun uncheckAll(shoppingListId:Long)
    }
    interface ItemsView{
        fun showItems(items:List<Item>)
        fun showItemsSummary()
        fun deleteItem(item: Item)
        fun deleteItems()
        fun checkItems()
        fun uncheckItems()
        fun showEmptyView()
        fun errorSaveItem()
        fun clearInputDescription()
    }
}