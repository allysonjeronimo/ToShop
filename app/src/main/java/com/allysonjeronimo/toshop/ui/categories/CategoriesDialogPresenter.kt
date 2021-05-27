package com.allysonjeronimo.toshop.ui.categories

import com.allysonjeronimo.toshop.legacy.facade.interfaces.ToShopFacade

class CategoriesDialogPresenter (
    private val view: CategoriesDialogContract.CategoriesDialogView,
    private val facade: ToShopFacade
) : CategoriesDialogContract.CategoriesDialogPresenter{

    override fun loadCategories(){
        val categories = facade.findAllCategories()
        view.showCategories(categories)
    }
}