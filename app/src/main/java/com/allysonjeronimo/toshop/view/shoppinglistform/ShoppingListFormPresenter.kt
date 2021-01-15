package com.allysonjeronimo.toshop.view.shoppinglistform

import com.allysonjeronimo.toshop.model.db.repositories.interfaces.ShoppingListRepository
import com.allysonjeronimo.toshop.model.entities.ShoppingList
import com.allysonjeronimo.toshop.model.facade.interfaces.ToShopFacade

class ShoppingListFormPresenter(
    private val view: ShoppingListFormContract.ShoppingListFormView,
    private val facade: ToShopFacade
) : ShoppingListFormContract.ShoppingListFormPresenter{

    override fun loadShoppingList(shoppingList:ShoppingList){
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