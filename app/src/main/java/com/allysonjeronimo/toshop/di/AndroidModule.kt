package com.allysonjeronimo.toshop.di

import com.allysonjeronimo.toshop.model.facade.ToShopFacadeImpl
import com.allysonjeronimo.toshop.model.facade.interfaces.ToShopFacade
import com.allysonjeronimo.toshop.view.categories.CategoriesDialogContract
import com.allysonjeronimo.toshop.view.categories.CategoriesDialogPresenter
import com.allysonjeronimo.toshop.view.itemform.ItemFormContract
import com.allysonjeronimo.toshop.view.itemform.ItemFormPresenter
import com.allysonjeronimo.toshop.view.items.ItemsContract
import com.allysonjeronimo.toshop.view.items.ItemsPresenter
import com.allysonjeronimo.toshop.view.shoppinglistform.ShoppingListFormContract
import com.allysonjeronimo.toshop.view.shoppinglistform.ShoppingListFormPresenter
import com.allysonjeronimo.toshop.view.shoppinglists.ShoppingListsContract
import com.allysonjeronimo.toshop.view.shoppinglists.ShoppingListsPresenter
import org.koin.dsl.module.module

val viewModule = module{
    factory { (view: ItemsContract.ItemsView) ->
        ItemsPresenter(
            view,
            facade = get()
        ) as ItemsContract.ItemsPresenter
    }

    factory{ (view: ItemFormContract.ItemFormView) ->
        ItemFormPresenter(
            view,
            facade = get()
        ) as ItemFormContract.ItemFormPresenter
    }

    factory{(view: ShoppingListsContract.ShoppingListsView) ->
        ShoppingListsPresenter(
            view,
            facade = get()
        ) as ShoppingListsContract.ShoppingListsPresenter
    }

    factory { (view: ShoppingListFormContract.ShoppingListFormView) ->
        ShoppingListFormPresenter(
            view,
            facade = get()
        ) as ShoppingListFormContract.ShoppingListFormPresenter
    }

    factory{
            (view : CategoriesDialogContract.CategoriesDialogView) ->
        CategoriesDialogPresenter(
            view,
            facade = get()
        ) as CategoriesDialogContract.CategoriesDialogPresenter
    }
}

val modelModule = module{
    single{ToShopFacadeImpl(context = get()) as ToShopFacade}
}