package com.itechpro.domain.model.domain

import com.itechpro.domain.model.login.Login
import com.itechpro.domain.model.navigationEvent.NavEvent
import com.itechpro.domain.model.navigationEvent.ProductNavEvent


data class DomainUiState(
    val isLoading: Boolean = false,
    val domain: String = "",
    val errorMessage: String = "",
    val navEvent: NavEvent = ProductNavEvent.None
)
