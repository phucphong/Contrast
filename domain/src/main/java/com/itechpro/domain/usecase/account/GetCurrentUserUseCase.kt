
package com.itechpro.domain.usecase.account

import com.itechpro.domain.model.UserModel
import com.itechpro.domain.repository.UserRepository
import javax.inject.Inject // ✅ Thêm import này

class GetCurrentUserUseCase @Inject constructor( // ✅ Thêm @Inject
    private val repository: UserRepository
) {
    suspend operator fun invoke(): UserModel {
        return repository.getCurrentUser()
    }
}
