package com.allysonjeronimo.toshop.domain.di

import com.allysonjeronimo.toshop.domain.interactor.category.FindAllCategories
import com.allysonjeronimo.toshop.domain.interactor.category.FindAllCategoriesUseCase
import com.allysonjeronimo.toshop.domain.interactor.item.*
import com.allysonjeronimo.toshop.domain.interactor.product.SearchProducts
import com.allysonjeronimo.toshop.domain.interactor.product.SearchProductsUseCase
import com.allysonjeronimo.toshop.domain.interactor.shoppinglist.*
import org.koin.dsl.module.module

val domainModule = module{
    // Category
    factory {
        FindAllCategories(repository = get()) as FindAllCategoriesUseCase
    }

    // Item
    factory {
        CheckItems(repository = get()) as CheckItemsUseCase
    }
    factory{
        CountItemsByShoppingList(repository = get()) as CountItemsByShoppingListUseCase
    }
    factory{
        DeleteItemsByShoppingList(repository = get()) as DeleteItemsByShoppingListUseCase
    }
    factory{
        DeleteItem(repository = get()) as DeleteItemUseCase
    }
    factory{
        FindItemsByShoppingList(repository = get()) as FindItemsByShoppingListUseCase
    }
    factory{
        SaveItem(repository = get()) as SaveItemUseCase
    }

    // Product
    factory{
        SearchProducts(repository = get()) as SearchProductsUseCase
    }

    // Shopping List
    factory{
        CountShoppingList(repository = get()) as CountShoppingListUseCase
    }
    factory{
        DeleteAllShoppingLists(repository = get()) as DeleteAllShoppingListsUseCase
    }
    factory{
        DeleteShoppingList(repository = get()) as DeleteShoppingListUseCase
    }
    factory{
        SaveShoppingList(repository = get()) as SaveShoppingListUseCase
    }
    factory{
        SearchShoppingLists(repository = get()) as SearchShoppingListsUseCase
    }
}