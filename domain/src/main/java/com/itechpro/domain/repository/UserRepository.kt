package com.itechpro.domain.repository

import com.itechpro.domain.model.UserModel


interface UserRepository {
    suspend fun getCurrentUser(): UserModel
}
