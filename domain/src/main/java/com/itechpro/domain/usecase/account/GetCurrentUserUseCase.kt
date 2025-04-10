package com.itechpro.domain.usecase.account

import com.itechpro.domain.model.CurrentUserInfo
import com.itechpro.domain.repository.UserRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): CurrentUserInfo {
        return repository.getCurrentUser()
    }
}
