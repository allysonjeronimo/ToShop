package com.allysonjeronimo.toshop.ui.shoppinglists

import com.allysonjeronimo.toshop.legacy.entities.ShoppingList
import com.allysonjeronimo.toshop.legacy.facade.interfaces.ToShopFacade

class ShoppingListsPresenter (
    private val view: ShoppingListsContract.ShoppingListsView,
    private val facade: ToShopFacade
) : ShoppingListsContract.ShoppingListsPresenter{

    override fun deleteShoppingList(shoppingList: ShoppingList){
        facade.deleteShoppingList(shoppingList)

        if(facade.countShoppingLists() > 0){
            view.deleteShoppingList(shoppingList)
        }
        else{
            view.showEmptyView()
        }
    }

    override fun deleteAllShoppingLists() {
        facade.deleteAllShoppingLists()
        view.showEmptyView()
    }

    override fun searchShoppingLists(term:String){

        val result =
            if(term.isNotEmpty())
                facade.searchShoppingLists(term)
            else
                facade.findAllShoppingLists()

        if(result.isNotEmpty()){
            view.showShoppingLists(result)
        }
        else{
            view.showEmptyView()
        }
    }
}