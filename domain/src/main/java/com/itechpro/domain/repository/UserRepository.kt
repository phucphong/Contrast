package com.itechpro.domain.repository

import com.itechpro.domain.model.CurrentUserInfo

interface UserRepository {
    suspend fun getCurrentUser(): CurrentUserInfo
}
