package com.contrast.Contrast.presentation.features.customer.viewmodel



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.contrast.Contrast.presentation.mapper.ValidationErrorMapper
import com.contrast.Contrast.utils.Common
import com.contrast.Contrast.utils.StringProvider
import com.itechpro.domain.model.Customer
import com.itechpro.domain.model.NetworkResponse


import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named
import com.contrast.Contrast.R
import com.contrast.Contrast.extensions.convertDateToYYYYMMDD
import com.contrast.Contrast.extensions.formatDateTimeDDMMYYYY

import com.itechpro.domain.enumApp.ValidationErrorType
import com.itechpro.domain.model.UserModel
import com.itechpro.domain.preferences.PreferencesManager
import com.itechpro.domain.usecase.account.GetCurrentUserUseCase
import com.itechpro.domain.usecase.customer.CheckEmailUseCase
import com.itechpro.domain.usecase.customer.CheckPhoneUseCase
import com.itechpro.domain.usecase.customer.CustomerInputValidator
import com.itechpro.domain.usecase.customer.CustomerUseCase

@HiltViewModel
class CustomerViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val addEditCustomerUseCase: CustomerUseCase,
    private val checkPhoneUseCase: CheckPhoneUseCase,
    private val checkEmailUseCase: CheckEmailUseCase,
    private val validator: CustomerInputValidator,
    private val stringProvider: StringProvider,
    private val preferencesManager: PreferencesManager,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
  
) : ViewModel() {

    private val _registerState = MutableStateFlow<NetworkResponse<List<Customer>>>(NetworkResponse.Loading)
    val registerState: StateFlow<NetworkResponse<List<Customer>>> = _registerState

    private val _validationError = MutableStateFlow<String?>(null)
    val validationError: StateFlow<String?> = _validationError
    var dateOfBirtday = ""

    private val _currentUser = MutableStateFlow<UserModel?>(null)
    val currentUser: StateFlow<UserModel?> = _currentUser
    private val device: String = preferencesManager.getDevice()
    private val authen: String = preferencesManager.getToken()
    private val idEmployee: String = preferencesManager.getEmployeeId()
    init {
        viewModelScope.launch {
            _currentUser.value = getCurrentUserUseCase()
        }
    }

    fun validateAndRegister(

        customerId: String,
        countryId: String,
        provinceId: String,
        districtId: String,
        wardId: String,

        statusId: String,
        infoSourceId: String,
        contactPersonId: String,
        introducerId: String,
        levelId: String,
        processId: String,
        careStaffId: String,
        policyGroupId: String,
        industryGroupId: String,

        code: String,
        salutation: String,
        fullName: String,
        phone: String,
        address: String,
        email: String?,
        website: String?,

        bankName: String,
        bankAccount: String,
        dateOfBirthEstablishmentDate: String,
        careDate: String,
        facebook: String,
        zalo: String,
        ggmap: String,
        note: String,
        noteWork: String,
        partnerId1: String,
        partnerId2: String,
        handoverData: String,
        isReferralAgent: String,
        createTask: Boolean,
        type: Boolean,
    ) {
      
        var mamenu = "themkhachhang"
        var content = "add"
        var dateOfBirth = ""
        var establishmentDate = ""
        if(customerId!="0"){

            mamenu = "suakhachhang"
            content = "edit"
        }
        var  typeData ="canhan"
        if(!type){
            establishmentDate = dateOfBirthEstablishmentDate
            dateOfBirth = ""
            typeData = "tochuc"
        }else{
            dateOfBirth = dateOfBirthEstablishmentDate
            establishmentDate = ""
        }

        val obj = Customer(
            id = customerId,
            ido = customerId,
            loai = typeData,
            idquocgia = countryId,
            idtinhthanh = provinceId,
            idquanhuyen = districtId,
            idphuongxa = wardId,
            idtrangthai = statusId,
            idnguonthongtin = infoSourceId,
            idnguoiphutrach = contactPersonId,
            idnguoigioithieu = introducerId,
            idcapdo = levelId,
            idquytrinh = processId,
            idnguoichamsoc = careStaffId,
            idnhomchinhsach = policyGroupId,
            idnhomnganhnghe = industryGroupId,
            ma = code,
            xungho = salutation,
            ten = fullName,
            dienthoai = phone,
            diachi = address,

            email = email ?: "",
            website = website ?: "",

            taikhoannganhang = bankName,
            tennganhang = bankAccount,

            ngaysinh = convertDateToYYYYMMDD(dateOfBirth),
            ngaythanhlapcongty = convertDateToYYYYMMDD(establishmentDate),
            ngaychamsoc = convertDateToYYYYMMDD(careDate),
            facebook = facebook,
            zalo = zalo,
            ggmap = ggmap,
//            datalanh = handoverData,
//            isdailygioithieu =isReferralAgent,

            ghichu = note,
            ghichucongviec = noteWork,
            taocongviec = createTask,
            iddoitac1 = partnerId1,
            iddoitac2 = partnerId2,
            mamenu = mamenu,
            hanhdong = "$phone - $fullName",
            noidungchinh = content,
            device = device
        )


        val result = validator.validateAll(fullName,contactPersonId,email)
        if (!result.success) {
            val error = ValidationErrorType.fromCode(result.message)
            _validationError.value = stringProvider.getString(ValidationErrorMapper.toMessageResId(error))
            return
        }
//        dateOfBirtday = "$selectedYear-$selectedMonth-$selectedDay"

        if(phone.isNotEmpty()){
            checkPhone(phone, email, customerId,obj)
        }else{
            addEditCustomer(customerId,obj)
        }

    }

    private fun checkPhone(phone: String, email: String?,ido: String, obj :Customer) {
        viewModelScope.launch(dispatcher) {
            try {

                checkPhoneUseCase.execute("khachhang", "checktrungdienthoai", phone, ido, authen).collect { result ->
                    when (result) {
                        is NetworkResponse.Success -> {
                            if (!result.data) {
                                if (!email.isNullOrEmpty()) {
                                    checkEmail(email, ido,obj)
                                } else {
                                    addEditCustomer(ido,obj)
                                }
                            } else {
                                _validationError.value = stringProvider.getString(R.string.registered_phone)
                            }
                        }
                        is NetworkResponse.Error -> _validationError.value = result.message
                        NetworkResponse.Loading -> {}
                    }
                }
            } catch (e: Exception) {
                _validationError.value = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}"
            }
        }
    }

    private fun checkEmail(email: String,ido: String, obj:Customer) {
        viewModelScope.launch(dispatcher) {
            try {

                checkEmailUseCase.execute("khachhang", "checkemail", email, idEmployee, authen).collect { result ->
                    when (result) {
                        is NetworkResponse.Success -> {
                            if (!result.data) {
                                addEditCustomer(ido,obj)
                            } else {
                                _validationError.value = stringProvider.getString(R.string.registered_email)
                            }
                        }
                        is NetworkResponse.Error -> _validationError.value = result.message
                        NetworkResponse.Loading -> {}
                    }
                }
            } catch (e: Exception) {
                _validationError.value = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}"
            }
        }
    }

    fun addEditCustomer(
        customerId: String,
        obj:Customer
    )
 {
        viewModelScope.launch(dispatcher) {
            _registerState.value = NetworkResponse.Loading
            try {
                var url = "/ex/api/addkhachhang"
               
                if(customerId!="0"){
                    url = "/ex/api/editkhachhang"
                }

                val result = addEditCustomerUseCase.invoke(url, obj,authen)
                _registerState.value = result
            } catch (e: Exception) {
                _registerState.value = NetworkResponse.Error(stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}")
            }
        }
    }

    fun clearValidationError() {
        _validationError.value = null
    }
}
