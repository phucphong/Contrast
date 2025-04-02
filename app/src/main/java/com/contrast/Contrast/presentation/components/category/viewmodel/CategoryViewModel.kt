package com.contrast.Contrast.presentation.components.category.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.R
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.contrast.Contrast.utils.Common
import com.contrast.Contrast.utils.StringProvider
import com.itechpro.domain.enumApp.CategoryType
import com.itechpro.domain.model.Category
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.usecase.category.CategoryUseCase
import com.itechpro.domain.usecase.catogory.FilterCategoryUseCase
import com.itechpro.domain.usecase.register.RegisterAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val filterCategoryUseCase: FilterCategoryUseCase,
    private val categoryUseCase: CategoryUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val stringProvider: StringProvider,
    @Named("deviceActive") var deviceActive: String,
    @Named("authen") private val authen: String,
    @Named("idEmployee") var idEmployee: String,
    @Named("isOffLine") var isOffLine: Boolean
) : ViewModel() {



    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    var searchText by mutableStateOf("")
    val filteredCategories: StateFlow<List<Category>>
        get() = filterCategoryUseCase(_categories, searchText)

    fun setSearch(text: String) {
        searchText = text
    }

    fun setData(data: List<Category>) {
        _categories.value = data
    }

    private val _validationError = MutableStateFlow<String?>(null)
    val validationError: StateFlow<String?> = _validationError

     fun getCategory( key: String, typeCheck: CategoryType) {
        viewModelScope.launch(dispatcher) {

            try {
                val token = if (!isOffLine) Common.key else authen
                categoryUseCase.getCategory(typeCheck, key, authen, ).collect { result ->
                    when (result) {
                        is NetworkResponse.Success -> {
                            setData(result.data)
                            _isLoading.value = false
                        }
                        is NetworkResponse.Error -> {
                            _validationError.value = result.message
                            _isLoading.value = false
                        }
                        NetworkResponse.Loading -> {
                            _isLoading.value = true

                        }
                    }
                }
            } catch (e: Exception) {
                _validationError.value = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}"
            } finally {
                _isLoading.value = false // ✅ Tắt loading dù thành công hay lỗi
            }
        }
    }


}
