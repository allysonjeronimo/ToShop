package com.allysonjeronimo.toshop.view.itemform

import com.allysonjeronimo.toshop.legacy.entities.Item

interface ItemFormContract {
    interface ItemFormPresenter{
        fun loadItem(item: Item)
        fun saveItem(item: Item) : Boolean
    }
    interface ItemFormView {
        fun showItem(item: Item)
        fun showDefaultValues()
        fun errorSaveItem()
    }
}