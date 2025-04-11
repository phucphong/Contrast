package com.contrast.Contrast.presentation.features.news.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.R
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.contrast.Contrast.utils.StringProvider
import com.itechpro.domain.model.Category
import com.itechpro.domain.model.CurrentUserInfo
import com.itechpro.domain.model.Customer
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.model.News
import com.itechpro.domain.model.UserModel
import com.itechpro.domain.usecase.account.GetCurrentUserUseCase
import com.itechpro.domain.usecase.news.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val newsUseCase: NewsUseCase,
    private val stringProvider: StringProvider,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _news = MutableStateFlow<List<News>>(emptyList())
    val news: StateFlow<List<News>> = _news


    private val _obj = MutableStateFlow<News?>(null)
    val obj: StateFlow<News?> = _obj

    private val _categoryNews = MutableStateFlow<List<Category>>(emptyList())
    val categoryNews: StateFlow<List<Category>> = _categoryNews

    private val _validationError = MutableStateFlow<String?>(null)
    val validationError: StateFlow<String?> = _validationError
    private val _domain = MutableStateFlow<String?>("")
    val domain: StateFlow<String?> = _domain

    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    private var currentUserInfo: CurrentUserInfo? = null

    init {
        viewModelScope.launch(dispatcher) {
            try {
                currentUserInfo = getCurrentUserUseCase()
                _domain.value = currentUserInfo!!.domain?:""
                getCategory()

            } catch (e: Exception) {
                _validationError.value = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}"
            }
        }
    }

    fun onTabSelected(index: Int) {
        _selectedTab.value = index
    }


    fun getNewDetail( ido: String) {
        viewModelScope.launch(dispatcher) {

            try {
                currentUserInfo?.let {
                    newsUseCase.getNewDetail(ido, it.token ).collect { result ->
                        when (result) {
                            is NetworkResponse.Success -> {

                                _obj.value = result.data

                            }

                            is NetworkResponse.Error -> {
                                _validationError.value = result.message

                            }

                            NetworkResponse.Loading -> {

                            }
                        }
                    }
                }
            } catch (e: Exception) {
                _validationError.value = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}"
            } finally {

            }
        }
    }

    fun getNews(idCategory: String) {
        val user = currentUserInfo ?: return

        viewModelScope.launch(dispatcher) {
            try {
                newsUseCase.getNews(user.isOfflineMode, idCategory, user.token).collect { result ->
                    when (result) {
                        is NetworkResponse.Loading -> {
                            _isLoading.value = true
                        }
                        is NetworkResponse.Success -> {
                            _isLoading.value = false
                            _news.value = result.data
                        }
                        is NetworkResponse.Error -> {
                            _isLoading.value = false
                            _validationError.value = result.message
                        }
                    }
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _validationError.value = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}"
            }
        }
    }


    fun getCategory() {
        val user = currentUserInfo ?: return

        viewModelScope.launch(dispatcher) {
            try {
                newsUseCase.getCategory(user.isOfflineMode, user.token).collect { result ->
                    when (result) {
                        is NetworkResponse.Success -> _categoryNews.value = result.data
                        is NetworkResponse.Error -> _validationError.value = result.message
                        NetworkResponse.Loading -> {}
                    }
                }
            } catch (e: Exception) {
                _validationError.value = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}"
            }
        }
    }
}
