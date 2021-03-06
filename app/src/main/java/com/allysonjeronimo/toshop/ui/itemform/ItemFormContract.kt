package com.allysonjeronimo.toshop.ui.itemform

import com.allysonjeronimo.toshop.data.legacy.entities.Item

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