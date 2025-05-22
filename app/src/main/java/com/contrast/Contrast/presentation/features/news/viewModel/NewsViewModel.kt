package com.contrast.Contrast.presentation.features.news.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.R
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.contrast.Contrast.extensions.collectResponse
import com.contrast.Contrast.utils.StringProvider
import com.itechpro.domain.model.CurrentUserInfo
import com.itechpro.domain.model.news.News
import com.itechpro.domain.model.navigationEvent.CartNavEvent
import com.itechpro.domain.model.navigationEvent.NavEvent
import com.itechpro.domain.model.navigationEvent.NewsNavEvent
import com.itechpro.domain.model.navigationEvent.NotificationNavEvent
import com.itechpro.domain.model.navigationEvent.ProductNavEvent

import com.itechpro.domain.model.news.NewsUiState
import com.itechpro.domain.model.product.Product
import com.itechpro.domain.usecase.account.GetCurrentUserUseCase
import com.itechpro.domain.usecase.news.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val useCase: NewsUseCase,
    private val stringProvider: StringProvider,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(NewsUiState())
    val state: StateFlow<NewsUiState> = _state

    private var currentUser: CurrentUserInfo? = null
    init {
        viewModelScope.launch(dispatcher) {
            val user = runCatching { getCurrentUserUseCase() }.getOrNull()
            currentUser = user
            if (user != null) {
                _state.update {
                    it.copy(domain = user.domain)
                }
            }
        }
    }


    fun setInitialNews(news: List<News>) {
        _state.update { it.copy(pagedNews = news.take(10)) }
    }

    fun onTabSelected(index: Int) {
        _state.update { it.copy(selectedTab = index) }
    }

    fun resetNavigation() {
        _state.update { it.copy(navEvent = ProductNavEvent.None) }
    }

    fun onItemNotificationSelected() {
        _state.update { it.copy(navEvent = NotificationNavEvent.GoToNotifications("", "")) }
    }

    fun onItemCarts() {
        _state.update { it.copy(navEvent = CartNavEvent.GoToCats) }
    }

    fun onItemNewSelected(product: News) {
        _state.update {
            it.copy(
                navEvent = NewsNavEvent.GoToNewDetail(
                    id = product.id ?: "",
                )
            )
        }
    }
    fun clearErrorMessage() {
        _state.update { it.copy(errorMessage = "") }
    }
    fun getNews(idCategory: String) {
        val user = currentUser ?: return
        viewModelScope.launch(dispatcher) {
            useCase.getNews(
                 idCategory, user.token ?: ""
            ).collectResponse(

                dispatcher = dispatcher,
                onLoading = { _state.update { it.copy(isLoading = true) } },
                onSuccess = { news ->
                    _state.update { it.copy(news = news, isLoading = false) }
                },
                onError = { msg ->
                    _state.update {
                        it.copy(
                            errorMessage = msg, isLoading = false
                        )
                    }
                })
        }
    }

    fun getNewDetail(id: String) {
        val user = currentUser ?: return
        viewModelScope.launch(dispatcher) {
            useCase.getNewDetail(
                 id, user.token ?: ""
            ).collectResponse(dispatcher = dispatcher,
                onLoading = {}, // Optional: handle if needed
                onSuccess = { detail -> _state.update { it.copy(newDetail = detail) } },
                onError = { msg -> _state.update { it.copy(errorMessage = msg) } })
        }
    }

    fun getCategory() {
        val user = currentUser ?: return
        viewModelScope.launch(dispatcher) {
            useCase.getCategory(
                user.token
            ).collectResponse(dispatcher = dispatcher,
                onLoading = { _state.update { it.copy(isLoading = true) } },
                onSuccess = { list ->
                    _state.update {
                        it.copy(
                            categoryNews = list, isLoading = false
                        )
                    }
                },
                onError = { msg ->
                    _state.update {
                        it.copy(
                            errorMessage = msg, isLoading = false
                        )
                    }
                })
        }
    }

    fun searchLocal(textSearch: String, list: List<News>) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            useCase.searchLocal(textSearch, list).collect { filteredList ->
                _state.update {
                    it.copy(
                        pagedNews = filteredList, isLoading = false, errorMessage = ""
                    )
                }
            }
        }
    }
}
