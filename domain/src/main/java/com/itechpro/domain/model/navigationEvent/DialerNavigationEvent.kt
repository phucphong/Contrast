package com.itechpro.domain.model.navigationEvent


sealed class DialerNavigationEvent {
    object GoToContact : DialerNavigationEvent()
    object GoToHistory : DialerNavigationEvent()
    object GoToContactPhone : DialerNavigationEvent()
    object GoToInternalCall : DialerNavigationEvent()
    object GoToSettings : DialerNavigationEvent()
}
