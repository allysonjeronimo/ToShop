package com.allysonjeronimo.toshop.ui.itemform

import com.allysonjeronimo.toshop.data.legacy.entities.Item
import com.allysonjeronimo.toshop.data.legacy.facade.interfaces.ToShopFacade

class ItemFormPresenter(
    private val view: ItemFormContract.ItemFormView,
    private val facade: ToShopFacade
) : ItemFormContract.ItemFormPresenter{

    override fun loadItem(item: Item){
        if(item.id != 0L){
            view.showItem(item)
        }
        else{
            view.showDefaultValues()
        }
    }

    override fun saveItem(item: Item) : Boolean{
        return try{
            facade.saveItem(item)
            true
        }catch(e:Exception){
            e.printStackTrace()
            view.errorSaveItem()
            false
        }
    }

}