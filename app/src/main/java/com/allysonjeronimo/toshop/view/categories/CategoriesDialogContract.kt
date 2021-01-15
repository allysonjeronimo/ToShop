package com.allysonjeronimo.toshop.view.categories

import com.allysonjeronimo.toshop.model.entities.Category

interface CategoriesDialogContract {
    interface CategoriesDialogPresenter{
        fun loadCategories()
    }
    interface CategoriesDialogView{
        fun showCategories(categories:List<Category>)
    }
}