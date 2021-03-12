package com.allysonjeronimo.toshop.data.di

import com.allysonjeronimo.toshop.data.db.AppDatabase
import com.allysonjeronimo.toshop.data.mapper.*
import com.allysonjeronimo.toshop.data.mapper.CategoryEntityToModelMapper
import com.allysonjeronimo.toshop.data.mapper.ItemEntityToModelMapper
import com.allysonjeronimo.toshop.data.mapper.ProductEntityToModelMapper
import com.allysonjeronimo.toshop.data.mapper.ShoppingListEntityToModelMapper
import com.allysonjeronimo.toshop.data.mapper.ShoppingListModelToEntityMapper
import com.allysonjeronimo.toshop.data.repository.CategoryRepositoryImpl
import com.allysonjeronimo.toshop.data.repository.ItemRepositoryImpl
import com.allysonjeronimo.toshop.data.repository.ProductRepositoryImpl
import com.allysonjeronimo.toshop.data.repository.ShoppingListRepositoryImpl
import com.allysonjeronimo.toshop.domain.repository.CategoryRepository
import com.allysonjeronimo.toshop.domain.repository.ProductRepository
import com.allysonjeronimo.toshop.domain.repository.ShoppingListRepository
import org.koin.dsl.module.module

val dataModule = module {
    factory{
        CategoryRepositoryImpl(
            AppDatabase.getInstance(context = get()).categoryDao(),
            CategoryEntityToModelMapper()
        ) as CategoryRepository
    }
    factory{
        ProductRepositoryImpl(
            AppDatabase.getInstance(context = get()).productDao(),
            ProductEntityToModelMapper()
        ) as ProductRepository
    }
    factory{
        ShoppingListRepositoryImpl(
            AppDatabase.getInstance(context = get()).shoppingListDao(),
            ShoppingListEntityToModelMapper(),
            ShoppingListModelToEntityMapper()
        ) as ShoppingListRepository
    }
    factory{
        ItemRepositoryImpl(
            AppDatabase.getInstance(context = get()).itemDao(),
            ItemEntityToModelMapper(),
            ItemModelToEntityMapper()
        )
    }
}