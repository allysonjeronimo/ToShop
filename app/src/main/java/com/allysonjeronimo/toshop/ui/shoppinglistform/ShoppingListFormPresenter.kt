package com.allysonjeronimo.toshop.ui.shoppinglistform

import com.allysonjeronimo.toshop.legacy.entities.ShoppingList
import com.allysonjeronimo.toshop.data.legacy.facade.interfaces.ToShopFacade

class ShoppingListFormPresenter(
    private val view: ShoppingListFormContract.ShoppingListFormView,
    private val facade: ToShopFacade
) : ShoppingListFormContract.ShoppingListFormPresenter{

    override fun loadShoppingList(shoppingList: ShoppingList){
        if(shoppingList.id != 0L){
            view.showShoppingList(shoppingList)
        }
        else{
            view.showDefaultValues()
        }
    }

    override fun saveShoppingList(shoppingList: ShoppingList) : Boolean{
        return try{
            facade.saveShoppingList(shoppingList)
            true
        }catch(e:Exception){
            e.printStackTrace()
            view.errorSaveShoppingList()
            false
        }
    }
}