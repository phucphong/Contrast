package com.itechpro.data.repository
import com.itechpro.data.api.oder.OderDetailAPI

import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.oder.OderTable
import com.itechpro.domain.repository.OderDetailRepository
import javax.inject.Inject

class OderDetailRepositoryImpl @Inject constructor(
    private val api: OderDetailAPI
) : OderDetailRepository {
    override suspend fun getOderById(
        obj: String,
        mode: String,
        ido: String,
        authen: String
    ): NetworkResponse<OderTable> {
        val response = api.getOderById("giohang",mode,ido,authen)
        return if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                NetworkResponse.Success(body)
            } else {
                NetworkResponse.Error("Cart data is null")
            }
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }





}
