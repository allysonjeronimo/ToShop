package com.allysonjeronimo.toshop.ui.shoppinglists

import com.allysonjeronimo.toshop.legacy.entities.ShoppingList

interface ShoppingListsContract {
    interface ShoppingListsPresenter{
        fun deleteShoppingList(shoppingList: ShoppingList)
        fun deleteAllShoppingLists()
        fun searchShoppingLists(term:String)
    }
    interface ShoppingListsView {
        fun showShoppingLists(shoppingLists: kotlin.collections.List<ShoppingList>)
        fun deleteShoppingList(shoppingList: ShoppingList)
        fun showEmptyView()
    }
}