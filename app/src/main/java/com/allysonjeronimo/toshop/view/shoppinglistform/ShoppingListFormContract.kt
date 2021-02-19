package com.allysonjeronimo.toshop.view.shoppinglistform

import com.allysonjeronimo.toshop.legacy.entities.ShoppingList

interface ShoppingListFormContract {
    interface ShoppingListFormPresenter{
        fun loadShoppingList(shoppingList: ShoppingList)
        fun saveShoppingList(shoppingList: ShoppingList) : Boolean
    }
    interface ShoppingListFormView {
        fun showShoppingList(shoppingList: ShoppingList)
        fun showDefaultValues()
        fun errorSaveShoppingList()
    }
}