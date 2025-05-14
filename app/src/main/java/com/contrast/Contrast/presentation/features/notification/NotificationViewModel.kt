package com.contrast.Contrast.presentation.features.notification



import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.R
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.contrast.Contrast.extensions.DateUtils
import com.contrast.Contrast.extensions.formatToYYYYMMDD
import com.contrast.Contrast.presentation.navigator.NotificationEventMapper
import com.contrast.Contrast.utils.StringProvider
import com.itechpro.domain.model.CurrentUserInfo
import com.itechpro.domain.model.category.Category
import com.itechpro.domain.model.navigationEvent.NavEvent
import com.itechpro.domain.model.navigationEvent.NotificationNavEvent
import com.itechpro.domain.model.navigationEvent.ProductNavEvent
import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.notifications.Notification
import com.itechpro.domain.model.notifications.NotificationUiState
import com.itechpro.domain.usecase.account.GetCurrentUserUseCase
import com.itechpro.domain.usecase.notification.NotificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val useCase: NotificationUseCase,
    private val stringProvider: StringProvider,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(NotificationUiState())
    val state: StateFlow<NotificationUiState> = _state

    private val _navigationEvent = MutableStateFlow<NavEvent>(ProductNavEvent.None)
    val navigationEvent: StateFlow<NavEvent> = _navigationEvent

    private var currentUserInfo: CurrentUserInfo? = null

    init {
        viewModelScope.launch(dispatcher) {
            runCatching { getCurrentUserUseCase() }
                .onSuccess { user ->
                    currentUserInfo = user
                    _state.update { it.copy(domain = user.domain.orEmpty()) }
                    getNotifications(DateUtils.today(), DateUtils.today())
                }
                .onFailure {
                    _state.update {
                        it.copy(validationError = stringProvider.getString(R.string.error_connection))
                    }
                }
        }
    }

    fun getNotifications(startDate: String, endDate: String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            useCase.getNotifications(
                formatToYYYYMMDD(startDate),
                formatToYYYYMMDD(endDate),
                user.token
            ).collect { result ->
                when (result) {
                    is NetworkResponse.Loading -> _state.update { it.copy(isLoading = true) }
                    is NetworkResponse.Success -> _state.update {
                        it.copy(
                            isLoading = false,
                            notifications = result.data.items,
                            totalNotificationItems = result.data.totalCount
                        )
                    }
                    is NetworkResponse.Error -> _state.update {
                        it.copy(isLoading = false, validationError = result.message)
                    }
                }
            }
        }
    }

    fun getNotificationDetail(ido: String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            useCase.getNotificationDetail(ido, user.token).collect { result ->
                _state.update {
                    it.copy(
                        isLoading = result is NetworkResponse.Loading,
                        notificationDetail = (result as? NetworkResponse.Success)?.data,
                        validationError = (result as? NetworkResponse.Error)?.message ?: it.validationError
                    )
                }
            }
        }
    }

    fun onNotificationItemClick(notification: Notification) {
        val navEvent = NotificationEventMapper.getEventFromNotification(notification)
        _state.update {
            it.copy(
                navEvent = navEvent

            )
        }
    }


    fun resetNavigation() {
        _navigationEvent.value = ProductNavEvent.None
    }


}
