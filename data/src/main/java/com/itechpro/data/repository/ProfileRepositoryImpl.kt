package com.itechpro.data.repository








import com.itechpro.data.api.ProfileAPI
import com.itechpro.domain.model.Account
import com.itechpro.domain.model.category.Category
import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val api: ProfileAPI
) : ProfileRepository {






    override suspend fun getMenuApp(type: String, authen: String): NetworkResponse<List<Category>> {
        val response = api.getMenuApp("100","1",type, authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }






    override suspend fun getQrCodeEmployee( obj: String, mode: String,authen: String): NetworkResponse<List<Account>> {
        val response = api.getQrCodeEmployee("tintuc","modedstintuc", authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }


    override suspend fun getQrCodeCustomer( obj: String, mode: String, ido: String,authen: String): NetworkResponse<List<Account>> {
        val response = api.getQrCodeCustomer(obj,mode, ido,authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }


    override suspend fun getInfoAccount(  idCustomer: String, typeAccount: String,authen: String): NetworkResponse<List<Account>> {
        val response = api.getInfoAccount("laythongtindangnhap","laythongtindangnhap_nhanvien", idCustomer, typeAccount ,authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }


    override suspend fun getInfoAccountEmployee(listparajson: String, authen: String): NetworkResponse<List<Account>> {
        val response = api.getInfoAccountEmployee(listparajson, authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }



}
