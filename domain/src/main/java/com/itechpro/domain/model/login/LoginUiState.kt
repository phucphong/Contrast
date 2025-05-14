package com.itechpro.domain.model.login

import com.itechpro.domain.model.navigationEvent.NavEvent
import com.itechpro.domain.model.navigationEvent.ProductNavEvent

data class LoginUiState(
    val isLoading: Boolean = false,
    val loginResult: Login? = null,
    val errorMessage: String = "",
    val domainLogin: String = "",
    val account: String = "",
    val password: String = "",
    val passwordBiometricAuthen: String = "",
    val validationError: String?= null,
    val rememberPassword: Boolean = false,
    val navigationEvent: NavEvent = ProductNavEvent.None
)
