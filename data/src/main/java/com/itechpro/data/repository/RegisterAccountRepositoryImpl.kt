package com.itechpro.data.repository



import com.itechpro.data.api.RegisterAccountAPI
import com.itechpro.domain.model.Account
import com.itechpro.domain.model.Column1
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.repository.RegisterAccountRepository
import javax.inject.Inject

class RegisterAccountRepositoryImpl @Inject constructor(
    private val api: RegisterAccountAPI
) : RegisterAccountRepository {



    override suspend fun registerAccount(url: String, account: Account): NetworkResponse<List<Account>> {
        return try {
            val response = api.registerAccount(url, account)
            if (response.isSuccessful) {
                NetworkResponse.Success(response.body() ?: emptyList())
            } else {
                NetworkResponse.Error("Lỗi: ${response.message()}")
            }
        } catch (e: Exception) {
            NetworkResponse.Error("Exception: ${e.message}")
        }
    }

    override suspend fun checkPhone(
        obj: String?,
        mode: String?,
        phone: String?,
        ido: String?,
        authen: String?
    ): NetworkResponse<List<Column1>> {
        return try {
            val response = api.checkPhone(obj, mode, phone, ido, authen)
            if (response.isSuccessful) {
                response.body()?.let {
                    NetworkResponse.Success(it)
                } ?: NetworkResponse.Error("Dữ liệu rỗng")
            } else {
                 NetworkResponse.Error("Lỗi: ${response.message()}")
            }
        } catch (e: Exception) {
            NetworkResponse.Error("Exception: ${e.message}")
        }
    }

    override suspend fun checkEmail(
        obj: String?,
        mode: String?,
        email: String?,
        idnhanvien: String?,
        authen: String?
    ): NetworkResponse<List<Column1>> {
        return try {
            val response = api.checkEmail(obj, mode, email, idnhanvien, authen)
            if (response.isSuccessful) {
                response.body()?.let {
                    NetworkResponse.Success(it)
                } ?: NetworkResponse.Error("Dữ liệu rỗng")
            } else {
                 NetworkResponse.Error("Lỗi: ${response.message()}")
            }
        } catch (e: Exception) {
            NetworkResponse.Error("Exception: ${e.message}")
        }
    }

    override suspend fun checkPhoneOff(
        obj: String?,
        mode: String?,
        phone: String?,
        referralPerson: String?,
        key: String?
    ): NetworkResponse<List<Column1>> {
        return try {
            val response = api.checkPhoneOff(obj, mode, phone, referralPerson, key)
            if (response.isSuccessful) {
                response.body()?.let {
                    NetworkResponse.Success(it)
                } ?: NetworkResponse.Error("Dữ liệu rỗng")
            } else {
                 NetworkResponse.Error("Lỗi: ${response.message()}")
            }
        } catch (e: Exception) {
            NetworkResponse.Error("Exception: ${e.message}")
        }
    }

    override suspend fun checkEmailOff(
        obj: String?,
        mode: String?,
        email: String?,
        referralPerson: String?,
        key: String?
    ): NetworkResponse<List<Column1>> {
        return try {
            val response = api.checkEmailOff(obj, mode, email, referralPerson, key)
            if (response.isSuccessful) {
                response.body()?.let {
                    NetworkResponse.Success(it)
                } ?: NetworkResponse.Error("Dữ liệu rỗng")
            } else {
                 NetworkResponse.Error("Lỗi: ${response.message()}")
            }
        } catch (e: Exception) {
            NetworkResponse.Error("Exception: ${e.message}")
        }
    }
}
