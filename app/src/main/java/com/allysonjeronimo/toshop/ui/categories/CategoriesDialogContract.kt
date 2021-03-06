package com.allysonjeronimo.toshop.ui.categories

import com.allysonjeronimo.toshop.data.legacy.entities.Category

interface CategoriesDialogContract {
    interface CategoriesDialogPresenter{
        fun loadCategories()
    }
    interface CategoriesDialogView{
        fun showCategories(categories:List<Category>)
    }
}