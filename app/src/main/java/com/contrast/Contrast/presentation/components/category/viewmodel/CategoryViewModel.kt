package com.contrast.Contrast.presentation.components.category.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.itechpro.domain.model.Category
import com.itechpro.domain.usecase.catogory.FilterCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val filterCategoryUseCase: FilterCategoryUseCase
) : ViewModel() {

    private val _categories = mutableStateListOf<Category>() // full list
    val categories: List<Category> get() = _categories

    var searchText by mutableStateOf("")
    val filteredCategories: List<Category>
        get() = filterCategoryUseCase(_categories, searchText)

    fun setSearch(text: String) {
        searchText = text
    }

    fun setData(data: List<Category>) {
        _categories.clear()
        _categories.addAll(data)
    }
}
