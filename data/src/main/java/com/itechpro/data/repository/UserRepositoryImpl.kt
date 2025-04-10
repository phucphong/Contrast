package com.itechpro.data.repository



import com.itechpro.data.local.LocalDataSource
import com.itechpro.domain.model.UserModel
import com.itechpro.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : UserRepository {

    override suspend fun getCurrentUser(): UserModel {
        return localDataSource.getCurrentUser()
    }
}
