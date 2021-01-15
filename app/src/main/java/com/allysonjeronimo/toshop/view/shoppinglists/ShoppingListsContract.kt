package com.allysonjeronimo.toshop.view.shoppinglists

import com.allysonjeronimo.toshop.model.entities.ShoppingList

interface ShoppingListsContract {
    interface ShoppingListsPresenter{
        fun deleteShoppingList(shoppingList:ShoppingList)
        fun deleteAllShoppingLists()
        fun searchShoppingLists(term:String)
    }
    interface ShoppingListsView {
        fun showShoppingLists(shoppingLists: kotlin.collections.List<ShoppingList>)
        fun deleteShoppingList(shoppingList: ShoppingList)
        fun showEmptyView()
    }
}