package com.allysonjeronimo.toshop.ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.allysonjeronimo.toshop.data.db.entity.Category
import com.allysonjeronimo.toshop.data.repository.CategoryRepository

class CategoriesViewModel(
    private val repository: CategoryRepository
) : ViewModel() {

    private val _categoriesLiveData = MutableLiveData<List<Category>>()

    val categoriesLiveData: LiveData<List<Category>>
        get() = _categoriesLiveData

    fun loadCategories(){

    }

    class CategoriesViewModelFactory(
        private val repository: CategoryRepository
    ) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>) =
            CategoriesViewModel(repository) as T
    }
}