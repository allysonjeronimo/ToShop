package com.allysonjeronimo.toshop.ui.categories

import androidx.lifecycle.*
import com.allysonjeronimo.toshop.data.db.entity.CategoryWithName
import com.allysonjeronimo.toshop.domain.repository.CategoryRepository
import kotlinx.coroutines.launch

class CategoriesViewModel(
    private val repository: com.allysonjeronimo.toshop.domain.repository.CategoryRepository
) : ViewModel() {

    private val _categoriesLiveData = MutableLiveData<List<CategoryWithName>>()
    private val _isLoadingLiveData = MutableLiveData<Boolean>()

    val categoriesLiveData: LiveData<List<CategoryWithName>>
        get() = _categoriesLiveData
    val isLoadingLiveData: LiveData<Boolean>
        get() = _isLoadingLiveData

    fun loadCategories(locale:String) = viewModelScope.launch {
        _isLoadingLiveData.value = true
        _categoriesLiveData.value = repository.findAll(locale)
        _isLoadingLiveData.value = false
    }

    class CategoriesViewModelFactory(
        private val repository: com.allysonjeronimo.toshop.domain.repository.CategoryRepository
    ) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>) =
            CategoriesViewModel(repository) as T
    }
}