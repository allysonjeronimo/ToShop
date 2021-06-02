package com.allysonjeronimo.toshop.domain.di

import com.allysonjeronimo.toshop.domain.interactor.category.FindAllCategoriesUseCase
import com.allysonjeronimo.toshop.domain.interactor.item.*
import com.allysonjeronimo.toshop.domain.interactor.product.SearchProductsUseCase
import com.allysonjeronimo.toshop.domain.interactor.shoppinglist.*
import org.koin.dsl.module.module

val domainModule = module{
    // Category
    factory {
        FindAllCategoriesUseCase(repository = get())
    }
    // Item
    factory {
        CheckItemUseCase(repository = get())
    }
    factory{
        CountItemsByShoppingListUseCase(repository = get())
    }
    factory{
        DeleteItemByShoppingListUseCase(repository = get())
    }
    factory{
        DeleteItemUseCase(repository = get())
    }
    factory{
        FindItemsByShoppingListUseCase(repository = get())
    }
    factory{
        SaveItemUseCase(repository = get())
    }
    // Product
    factory{
        SearchProductsUseCase(repository = get())
    }
    // Shopping List
    factory{
        CountShoppingListUseCase(repository = get())
    }
    factory{
        DeleteAllShoppingListsUseCase(repository = get())
    }
    factory{
        DeleteShoppingListUseCase(repository = get())
    }
    factory{
        SaveShoppingListUseCase(repository = get())
    }
    factory{
        SearchShoppingListsUseCase(repository = get())
    }
}