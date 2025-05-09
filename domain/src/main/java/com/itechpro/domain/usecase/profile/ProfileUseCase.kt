package com.itechpro.domain.usecase.profile


import com.itechpro.domain.model.Account
import com.itechpro.domain.model.Category
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.model.cart.CartResult
import com.itechpro.domain.model.profile.ProfileResult

import com.itechpro.domain.repository.ProfileRepository
import com.itechpro.domain.safeFlowCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONObject
import javax.inject.Inject

class ProfileUseCase @Inject constructor(
    private val repository: ProfileRepository,

    ) {


    fun getMenuApp(typeAccount: String, authen: String): Flow<NetworkResponse<ProfileResult>> {
        return flow {
            emit(NetworkResponse.Loading)
            var coachings: ArrayList<Category> = arrayListOf()
            var qACoachings: ArrayList<Category> = arrayListOf()

            val categorys: ArrayList<Category> = arrayListOf()
            val oders: ArrayList<Category> = arrayListOf()
            val agencys: ArrayList<Category> = arrayListOf()

            when (val result = repository.getMenuApp("khachhang", authen)) {
                is NetworkResponse.Success -> {
                    val items = result.data

                    items.forEach { obj ->

                        val key = obj.ma ?: ""
                        val active = obj.hoatdong ?:false
                        when (key ) {
                            "coachinh121" ->  if (active)coachings.add(obj)
                            "groupcoaching" ->  if (active)coachings.add(obj)
                            "khoahocdadangky" ->  if (active)coachings.add(obj)
                            "khoahoccuatoi" -> if (active) coachings.add(obj)
                            "lichdaotao" ->  if (active)coachings.add(obj)
                            "kehoachcoaching" ->  if (active)coachings.add(obj)
                            "lichcoaching" -> if (active) coachings.add(obj)
                            "tiendokehoachcoaching" -> if (active) coachings.add(obj)
                            "videoyeuthich" -> if (active)coachings.add(obj)

                            "sanphamquantam" -> if (active) coachings.add(obj)
                            "banquantam" ->  if (active)coachings.add(obj)

                            "thanhtoankhachhang" -> if (active) coachings.add(obj)
                            "thoigianthanhtoan" ->  if (active)coachings.add(obj)
                            "baocaothanhtoankh" -> if (active) coachings.add(obj)
                            "baocaothanhtoankh" ->  if (active)coachings.add(obj)

                            "hoidapcoach" -> if (active) qACoachings.add(obj)
                            "hoivadap" ->  if (active)qACoachings.add(obj)
                            "hoivadap" ->  if (active)qACoachings.add(obj)
                            
                            "donhangchoxacnhan" -> if (active) oders.add(obj)
                            "donhangdaxacnhan" -> if (active) oders.add(obj)
                            "donhang" ->  if (active)oders.add(obj)

                            "qrcodegiothieu" -> if (active) categorys.add(obj)
                            "tiepthilienket" -> {
                                if (typeAccount != "khachhang") {
                                    if (active) categorys.add(obj)}
                            }
                            "thunhap" ->  {
                                if (typeAccount != "khachhang") {
                                    if (active) categorys.add(obj)}
                            }
                            "sanphamdaxem" -> if (active) categorys.add(obj)
                            "sanphamdaluu" -> if (active) categorys.add(obj)
                            "lieutrinhdangthuchien" ->  if (active)categorys.add(obj)
                            "lichthuchiendichvu" ->  if (active)categorys.add(obj)
                            "huongdanspatainha" ->  if (active)categorys.add(obj)
//                            "nangcapthanhdaily" ->  if (active)categorys.add(obj)
                            "dailycapduoi" -> if (active) {
                                if (typeAccount != "khachhang") {
                                    categorys.add(obj)
                                }
                            }

                            "cantuvan" ->  if (active){
                                if (typeAccount != "khachhang") {
                                    categorys.add(obj)
                                }
                            }

                            "doanhsotieudungcanhan" ->  if (active){
                                if (typeAccount != "khachhang") {
                                    categorys.add(obj)
                                }
                            }

                            "baocaodoanhsotheotungdaily" ->  if (active){
                                if (typeAccount != "khachhang") {
                                    categorys.add(obj)
                                }
                            }

                            "baocaodoanhsotheotungdaily" ->  if (active){
                                if (typeAccount != "khachhang") {
                                    categorys.add(obj)
                                }
                            }

                            "baocaohoahongthudong" -> if (active) {
                                if (typeAccount != "khachhang") {
                                    categorys.add(obj)
                                }
                            }

                            "baocaothuongthangcap" -> if (active) {
                                if (typeAccount != "khachhang") {
                                    categorys.add(obj)
                                }
                            }

                            "baocaothuongthangcapcanhan" ->  if (active){
                                if (typeAccount != "khachhang") {
                                    categorys.add(obj)
                                }
                            }




                        }


                    }

                    emit(
                        NetworkResponse.Success(
                            ProfileResult(
                                oders = oders,
                                categorys = categorys,

                                )
                        )
                    )

                }

                is NetworkResponse.Error -> {
                    emit(NetworkResponse.Error(result.message))
                }

                NetworkResponse.Loading -> {}
            }


        }.flowOn(Dispatchers.IO)
    }


    fun getQrCodeEmployee(
        authen: String
    ): Flow<NetworkResponse<String>> = safeFlowCall {
        val response = repository.getQrCodeEmployee("layqrcode", "modelayqrcodenhanvien", authen)
        when (response) {
            is NetworkResponse.Success -> {
                val customer = response.data.firstOrNull()?.kq ?: ""
                customer?.let { NetworkResponse.Success(it) } ?: NetworkResponse.Error("Error")
            }

            is NetworkResponse.Error -> NetworkResponse.Error(response.message)
            is NetworkResponse.Loading -> NetworkResponse.Loading
        }
    }

    fun getQrCodeCustomer(
        ido: String,
        authen: String
    ): Flow<NetworkResponse<String>> = safeFlowCall {
        val response = repository.getQrCodeCustomer("layqrcode", "modelayqrcode", ido,authen)
        when (response) {
            is NetworkResponse.Success -> {
                val customer = response.data.firstOrNull()?.kq ?: ""
                customer?.let { NetworkResponse.Success(it) } ?: NetworkResponse.Error("Error")
            }

            is NetworkResponse.Error -> NetworkResponse.Error(response.message)
            is NetworkResponse.Loading -> NetworkResponse.Loading
        }
    }


    fun getInfoAccountEmployee(
        ids: String, authen: String
    ): Flow<NetworkResponse<Account>> = safeFlowCall {
        val listparajson = JSONObject()
        try {
            listparajson.put("obj", "laychitietnhanvien")
            listparajson.put("ids", ids)
        } catch (e: Exception) {
        }

        val response = repository.getInfoAccountEmployee(listparajson.toString(), authen)
        when (response) {
            is NetworkResponse.Success -> {
                val customer = response.data.firstOrNull()
                customer?.let { NetworkResponse.Success(it) } ?: NetworkResponse.Error("Error")
            }

            is NetworkResponse.Error -> NetworkResponse.Error(response.message)
            is NetworkResponse.Loading -> NetworkResponse.Loading
        }
    }

    fun getInfoAccount(
        idCustomer: String, typeAccount: String, authen: String
    ): Flow<NetworkResponse<Account>> = safeFlowCall {

        val response = repository.getInfoAccount(idCustomer, typeAccount, authen)
        when (response) {
            is NetworkResponse.Success -> {
                val customer = response.data.firstOrNull()
                customer?.let { NetworkResponse.Success(it) } ?: NetworkResponse.Error("Error")
            }

            is NetworkResponse.Error -> NetworkResponse.Error(response.message)
            is NetworkResponse.Loading -> NetworkResponse.Loading
        }
    }


}
