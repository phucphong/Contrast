package com.contrast.Contrast.presentation.features.notification


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.R
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.contrast.Contrast.extensions.DateUtils
import com.contrast.Contrast.extensions.formatToYYYYMMDD
import com.contrast.Contrast.presentation.navigator.NotificationEventMapper
import com.contrast.Contrast.utils.StringProvider
import com.itechpro.domain.model.Category
import com.itechpro.domain.model.CurrentUserInfo
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.model.notifications.Notification
import com.itechpro.domain.model.navigationEvent.NavEvent
import com.itechpro.domain.model.navigationEvent.NotificationNavEvent
import com.itechpro.domain.model.navigationEvent.ProductNavEvent

import com.itechpro.domain.usecase.account.GetCurrentUserUseCase
import com.itechpro.domain.usecase.notification.NotificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val _notifications = MutableStateFlow<List<Notification>>(emptyList())
    val notifications: StateFlow<List<Notification>> = _notifications
    private val _obj = MutableStateFlow<Notification?>(null)
    val obj: StateFlow<Notification?> = _obj



    private val _validationError = MutableStateFlow("")
    val validationError: StateFlow<String> = _validationError

    private val _domain = MutableStateFlow("")
    val domain: StateFlow<String> = _domain

    private val _statusMessage = MutableStateFlow("")
    val statusMessage: StateFlow<String> = _statusMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading


    private val _totalNotificationItems = MutableStateFlow(0)
    val totalNotificationItems: StateFlow<Int> = _totalNotificationItems


    private val _navigationEvent = MutableStateFlow<NavEvent>(ProductNavEvent.None)
    val navigationEvent: StateFlow<NavEvent> = _navigationEvent
    private var currentUserInfo: CurrentUserInfo? = null

    init {
        viewModelScope.launch(dispatcher) {
            try {
                currentUserInfo = getCurrentUserUseCase()
                _domain.value = currentUserInfo?.domain.orEmpty()


                val notificationsDeferred = async {  getNotifications(DateUtils.today(),DateUtils.today())}
                notificationsDeferred.await()
            } catch (e: Exception) {
                _validationError.value = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage.orEmpty()}"
            }
        }
    }
    fun onTabSelected(index: Int, category: Category) {
        _navigationEvent.value = ProductNavEvent.GoToProductsCategory(category.id.orEmpty())
    }

    fun onNotificationItemClick(obj: Notification) {
        val navEvent = NotificationEventMapper.getEventFromNotification(obj)
        _navigationEvent.value = navEvent
    }

    fun getNotifications(startDate: String,endDate: String) {
        val user = currentUserInfo ?: return

        val start = System.currentTimeMillis()

        viewModelScope.launch(dispatcher) {
            useCase.getNotifications(formatToYYYYMMDD(startDate),formatToYYYYMMDD(endDate),user.token).collect { result ->
                when (result) {
                    is NetworkResponse.Loading -> _isLoading.value = true
                    is NetworkResponse.Success -> {
                        _isLoading.value = false
                        _notifications.value = result.data.items
                        _totalNotificationItems.value = result.data.totalCount

                        val end = System.currentTimeMillis()
                        Log.d("⏱️Notification", "✅ Finished useCase, duration = ${end - start}ms")
                    }
                    is NetworkResponse.Error -> {
                        _isLoading.value = false
                        _validationError.value = result.message
                    }
                }
            }
        }
    }

    fun getNotificationDetail(ido: String) {
        val user = currentUserInfo ?: return
        viewModelScope.launch(dispatcher) {
            useCase.getNotificationDetail(ido, user.token).collect { result ->
                _isLoading.value = result is NetworkResponse.Loading
                if (result is NetworkResponse.Success) _obj.value = result.data
                if (result is NetworkResponse.Error) _validationError.value = result.message
            }
        }
    }




    fun resetNavigation() {
        _navigationEvent.value = ProductNavEvent.None
    }

    companion object {
        private val notificationEventMap: Map<String, (Notification) -> NotificationNavEvent> = mapOf(
            "thongbaodonhangmoi" to { obj -> NotificationNavEvent.GoToOrderDetail(obj.iddoituong ?: "") },
            "cohoikinhdoanh" to { obj -> NotificationNavEvent.GoToOpportunityDetail(obj.iddoituong ?: "") },
            "duan" to { obj -> NotificationNavEvent.GoToProjectDetail(obj.iddoituong ?: "") },
            "khachhang" to { obj -> NotificationNavEvent.GoToCustomerDetail(obj.iddoituong ?: "") },
            "congvieckhac" to { obj -> NotificationNavEvent.GoToTaskDetail(obj.iddoituong ?: "") },
            "congvieccohoikinhdoanh" to { obj -> NotificationNavEvent.GoToTaskDetail(obj.iddoituong ?: "") },
            "congviecduan" to { obj -> NotificationNavEvent.GoToTaskDetail(obj.iddoituong ?: "") },
            // ... vân vân
        )
    }

}
