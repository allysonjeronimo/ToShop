package com.allysonjeronimo.toshop.view.items

import com.allysonjeronimo.toshop.legacy.entities.Item
import com.allysonjeronimo.toshop.legacy.entities.Product
import com.allysonjeronimo.toshop.legacy.entities.ShoppingList
import com.allysonjeronimo.toshop.legacy.facade.interfaces.ToShopFacade

class ItemsPresenter(
    private val view: ItemsContract.ItemsView,
    private val facade: ToShopFacade
) : ItemsContract.ItemsPresenter{

    override fun searchProducts(term:String) : List<Product>{
        return facade.searchProducts(term)
    }

    override fun checkItem(item: Item){
        item.purchased = !item.purchased
        facade.saveItem(item)
        view.showItemsSummary()
    }

    override fun loadItems(shoppingListId:Long){
        val items = facade.findItemsByShoppingList(shoppingListId)
        if(items.isNotEmpty()){
            view.showItems(items)
        }
        else{
            view.showEmptyView()
        }
        view.showItemsSummary()
    }

    override fun deleteItem(item: Item){
        facade.deleteItem(item)
        view.deleteItem(item)
        view.showItemsSummary()
        if(facade.countItemsByShoppingList(item.listId) == 0){
            view.showEmptyView()
        }
    }

    override fun saveItem(item: Item) : Boolean{
        return try{
            facade.saveItem(item)
            view.clearInputDescription()
            loadItems(item.listId)
            true
        }catch(e:Exception){
            e.printStackTrace()
            view.errorSaveItem()
            false
        }
    }

    override fun deleteShoppingList(shoppingListId: Long){
        facade.deleteShoppingList(ShoppingList(id=shoppingListId))
    }

    override fun deleteAll(shoppingListId:Long){
        facade.deleteItems(shoppingListId)
        view.deleteItems()
        view.showItemsSummary()
        view.showEmptyView()
    }

    override fun checkAll(shoppingListId:Long){
        facade.updateItemsPurchasedByShoppingList(true, shoppingListId)
        view.checkItems()
        view.showItemsSummary()
    }

    override fun uncheckAll(shoppingListId:Long){
        facade.updateItemsPurchasedByShoppingList(false, shoppingListId)
        view.uncheckItems()
        view.showItemsSummary()
    }
}