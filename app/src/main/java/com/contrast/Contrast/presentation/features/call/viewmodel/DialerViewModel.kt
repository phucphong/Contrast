package com.contrast.Contrast.presentation.features.call.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.R
import com.contrast.Contrast.utils.StringProvider
import com.itechpro.domain.model.BottomActionItem
import com.itechpro.domain.model.navigationEvent.DialerNavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DialerViewModel @Inject constructor(
    private val stringProvider: StringProvider,

    ) : ViewModel() {

    // State lưu số đang bấm
    private val _inputNumber = MutableStateFlow("")
    val inputNumber: StateFlow<String> = _inputNumber
    private val _navigationEvent = MutableSharedFlow<DialerNavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun handleContact() {
        viewModelScope.launch {
            _navigationEvent.emit(DialerNavigationEvent.GoToContact)
        }
    }
    fun handleHistory() {
        viewModelScope.launch {
            _navigationEvent.emit(DialerNavigationEvent.GoToHistory)
        }
    }
    fun handleContactPhone() {
        viewModelScope.launch {
            _navigationEvent.emit(DialerNavigationEvent.GoToContactPhone)
        }
    }

    fun handleInternalCall() {
        viewModelScope.launch {
            _navigationEvent.emit(DialerNavigationEvent.GoToInternalCall)
        }
    }

    fun handleSettings() {
        viewModelScope.launch {
            _navigationEvent.emit(DialerNavigationEvent.GoToSettings)
        }
    }




    fun onNumberClick(number: String) {
        _inputNumber.value += number
    }

    fun onBackspace() {
        _inputNumber.value = _inputNumber.value.dropLast(1)
    }

    fun onClear() {
        _inputNumber.value = ""
    }
    fun onAddContact() {

    }

    fun onCall() {
        // TODO: trigger call logic
    }
    val dialPad: List<List<String>> = listOf(
        listOf("1", "2", "3"),
        listOf("4", "5", "6"),
        listOf("7", "8", "9"),
        listOf("*", "0", "#")
    )

    val bottomActions = listOf(
        BottomActionItem(
            id = "0",
            iconRes = R.drawable.ic_contact,
            label = stringProvider.getString(R.string.contact)
        ),
        BottomActionItem(
            id = "1",
            iconRes = R.drawable.recents,
            label = stringProvider.getString(R.string.history)
        ),
        BottomActionItem(
            id = "2",
            iconRes = R.drawable.contact,
            label = stringProvider.getString(R.string.contact_phone)
        ),
        BottomActionItem(
            id = "3",
            iconRes = R.drawable.call_internal,
            label = stringProvider.getString(R.string.call_internal)
        ),
        BottomActionItem(
            id = "4",
            iconRes = R.drawable.setting,
            label = stringProvider.getString(R.string.settings)
        )
    )

    fun onBottomActionClick(id: String) {
        when (id) {
            "0" -> handleContact()
            "1" -> handleHistory()
            "2" -> handleContactPhone()
            "3" -> handleInternalCall()
            "4" -> handleSettings()
        }
    }



}
