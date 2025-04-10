package com.contrast.Contrast.presentation.features.customer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.R
import com.contrast.Contrast.di.qualifier.IoDispatcher
import com.contrast.Contrast.extensions.convertDateToYYYYMMDD
import com.contrast.Contrast.presentation.mapper.ValidationErrorMapper
import com.contrast.Contrast.utils.StringProvider
import com.itechpro.domain.enumApp.CategoryType
import com.itechpro.domain.enumApp.ValidationErrorType
import com.itechpro.domain.model.CurrentUserInfo
import com.itechpro.domain.model.Customer
import com.itechpro.domain.model.InfoDetail
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.model.UserModel
import com.itechpro.domain.usecase.account.GetCurrentUserUseCase
import com.itechpro.domain.usecase.customer.CheckEmailUseCase
import com.itechpro.domain.usecase.customer.CheckPhoneUseCase
import com.itechpro.domain.usecase.customer.CustomerInputValidator
import com.itechpro.domain.usecase.customer.CustomerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val customerUseCase: CustomerUseCase,
    private val checkPhoneUseCase: CheckPhoneUseCase,
    private val checkEmailUseCase: CheckEmailUseCase,
    private val validator: CustomerInputValidator,
    private val stringProvider: StringProvider,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _registerState = MutableStateFlow<NetworkResponse<List<Customer>>>(NetworkResponse.Loading)
    val registerState: StateFlow<NetworkResponse<List<Customer>>> = _registerState

    private val _validationError = MutableStateFlow<String?>(null)
    val validationError: StateFlow<String?> = _validationError

    private val _currentUser = MutableStateFlow<UserModel?>(null)
    val currentUser: StateFlow<UserModel?> = _currentUser

    private val _obj = MutableStateFlow<Customer?>(null)
    val obj: StateFlow<Customer?> = _obj
    private val _customerInfo = MutableStateFlow<List<InfoDetail>?>(null)
    val customerInfo: StateFlow<List<InfoDetail>?> = _customerInfo
    private val _customerOther = MutableStateFlow<List<InfoDetail>?>(null)
    val customerOther: StateFlow<List<InfoDetail>?> = _customerOther

    private val _domainCustomer = MutableStateFlow("")
    val domainCustomer: StateFlow<String> = _domainCustomer

    private lateinit var currentUserInfo: CurrentUserInfo

    init {
        viewModelScope.launch {
            currentUserInfo = getCurrentUserUseCase()
            _currentUser.value = UserModel(currentUserInfo.employeeId, currentUserInfo.employeeName)
            _domainCustomer.value = currentUserInfo.domainCustomer
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
        val validationResult = validator.validateAll(fullName, contactPersonId, email)
        if (!validationResult.success) {
            handleValidationError(validationResult.message)
            return
        }

        val customer = buildCustomer(
            customerId,
            countryId,
            provinceId,
            districtId,
            wardId,
            statusId,
            infoSourceId,
            contactPersonId,
            introducerId,
            levelId,
            processId,
            careStaffId,
            policyGroupId,
            industryGroupId,
            code,
            salutation,
            fullName,
            phone,
            address,
            email,
            website,
            dateOfBirthEstablishmentDate,
            careDate,
            bankName,
            bankAccount,
            facebook,
            zalo,
            ggmap,
            note,
            noteWork,
            partnerId1,
            partnerId2,
            handoverData,
            isReferralAgent,
            createTask,
            type
        )

        if (phone.isNotEmpty()) {
            checkPhone(phone, email, customerId, customer)
        } else {
            addEditCustomer(customerId, customer)
        }
    }

    private fun buildCustomer(
        id: String,
        idCountry: String,
        idProvince: String,
        idDistrict: String,
        idWard: String,
        idStatus: String,
        idSource: String,
        idContact: String,
        idIntroducer: String,
        idLevel: String,
        idProcess: String,
        idCareStaff: String,
        idPolicyGroup: String,
        idIndustry: String,
        code: String,
        salutation: String,
        fullName: String,
        phone: String,
        address: String,
        email: String?,
        website: String?,
        dobOrEstDate: String,
        careDate: String,
        bankName: String,
        bankAccount: String,
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
        isPersonal: Boolean
    ): Customer {
        val dob = if (isPersonal) convertDateToYYYYMMDD(dobOrEstDate) else ""
        val estDate = if (!isPersonal) convertDateToYYYYMMDD(dobOrEstDate) else ""

        return Customer(
            id = id,
            ido = id,
            loai = if (isPersonal) "canhan" else "tochuc",
            idquocgia = idCountry,
            idtinhthanh = idProvince,
            idquanhuyen = idDistrict,
            idphuongxa = idWard,
            idtrangthai = idStatus,
            idnguonthongtin = idSource,
            idnguoiphutrach = idContact,
            idnguoigioithieu = idIntroducer,
            idcapdo = idLevel,
            idquytrinh = idProcess,
            idnguoichamsoc = idCareStaff,
            idnhomchinhsach = idPolicyGroup,
            idnhomnganhnghe = idIndustry,
            ma = code,
            xungho = salutation,
            ten = fullName,
            dienthoai = phone,
            diachi = address,
            email = email ?: "",
            website = website ?: "",
            taikhoannganhang = bankName,
            tennganhang = bankAccount,
            ngaysinh = dob,
            ngaythanhlapcongty = estDate,
            ngaychamsoc = convertDateToYYYYMMDD(careDate),
            facebook = facebook,
            zalo = zalo,
            ggmap = ggmap,
            ghichu = note,
            ghichucongviec = noteWork,
            taocongviec = createTask,
            iddoitac1 = partnerId1,
            iddoitac2 = partnerId2,
            mamenu = if (id != "0") "suakhachhang" else "themkhachhang",
            hanhdong = "$phone - $fullName",
            noidungchinh = if (id != "0") "edit" else "add",
            device = currentUserInfo.device
        )
    }

    private fun handleValidationError(errorCode: String?) {
        val error = ValidationErrorType.fromCode(errorCode)
        _validationError.value = stringProvider.getString(ValidationErrorMapper.toMessageResId(error))
    }

    private fun checkPhone(phone: String, email: String?, id: String, customer: Customer) {
        viewModelScope.launch(dispatcher) {
            try {
                checkPhoneUseCase.execute("khachhang", "checktrungdienthoai", phone, id, currentUserInfo.token).collect { result ->
                    when (result) {
                        is NetworkResponse.Success -> {
                            if (!result.data) {
                                if (!email.isNullOrEmpty()) checkEmail(email, id, customer)
                                else addEditCustomer(id, customer)
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

    private fun checkEmail(email: String, id: String, customer: Customer) {
        viewModelScope.launch(dispatcher) {
            try {
                checkEmailUseCase.execute("khachhang", "checkemail", email, currentUserInfo.employeeId, currentUserInfo.token).collect { result ->
                    when (result) {
                        is NetworkResponse.Success -> {
                            if (!result.data) addEditCustomer(id, customer)
                            else _validationError.value = stringProvider.getString(R.string.registered_email)
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


    fun customerDetail( ido: String) {
        viewModelScope.launch(dispatcher) {

            try {
                customerUseCase.customerDetail(ido, currentUserInfo.token ).collect { result ->
                    when (result) {
                        is NetworkResponse.Success -> {

                            _obj.value = result.data
                            _customerInfo.value = mapToInfoDetail(result.data)
                            _customerOther.value = mapToCustomerInfoOtherList(result.data)
                        }
                        is NetworkResponse.Error -> {
                            _validationError.value = result.message

                        }
                        NetworkResponse.Loading -> {

                        }
                    }
                }
            } catch (e: Exception) {
                _validationError.value = stringProvider.getString(R.string.error_connection) + ": ${e.localizedMessage ?: ""}"
            } finally {

            }
        }
    }
    private fun mapToInfoDetail(data: Customer): List<InfoDetail> {
        val  type = data.loai?:""
        return listOf(

            InfoDetail(label = stringProvider.getString(R.string.customer_type), value =data.ten?:""),
            InfoDetail(label = stringProvider.getString(R.string.customer_code), value =data.ma?:""),
            InfoDetail(label = stringProvider.getString(R.string.customer_name), value =data.ten?:""),
            InfoDetail(label = stringProvider.getString(R.string.phone_number), value = data.dienthoai1?:"", highlight = true),
            InfoDetail(label = stringProvider.getString(R.string.address), value =data.diachi?:""),

            InfoDetail(label = if(type=="canhan")stringProvider.getString(R.string.date_of_birtd)else stringProvider.getString(R.string.established_date) , value =if(type=="canhan")data.ngaysinh?:"" else data.ngaythanhlapcongty?:""),
            InfoDetail(label = stringProvider.getString(R.string.email), value = data.email?:""),
            InfoDetail(label = stringProvider.getString(R.string.website), value =data.website?:""),
            InfoDetail(label = stringProvider.getString(R.string.map), value = data.gmap?:""),
            InfoDetail(label = stringProvider.getString(R.string.level), value = data.tencapdo?:""),
            InfoDetail(label = stringProvider.getString(R.string.policy_group), value =data.tennhomchinhsach?:""),
            InfoDetail(label = stringProvider.getString(R.string.industry_group), value = data.tennhomnganhghe?:""),
            InfoDetail(label = stringProvider.getString(R.string.status), value = data.tentrangthai?:""),
            InfoDetail(label = stringProvider.getString(R.string.country), value = data.tenquocgia?:""),
            InfoDetail(label = stringProvider.getString(R.string.province), value =data.tentinhthanh?:""),
            InfoDetail(label = stringProvider.getString(R.string.district), value = data.tenquanhuyen?:""),
            InfoDetail(label = stringProvider.getString(R.string.ward), value = data.tenphuongxa?:"")
        )
    }


    fun mapToCustomerInfoOtherList(
        data: Customer
    ): List<InfoDetail> {
        return listOf(
            InfoDetail(stringProvider.getString(R.string.customer_category), data.loaikhachhangtudong ?: ""),
            InfoDetail(stringProvider.getString(R.string.auto_type), data.loaikhachhangtudong ?: ""),
            InfoDetail(stringProvider.getString(R.string.information_source), data.tennguonthongtin ?: ""),
            InfoDetail(stringProvider.getString(R.string.bank_account), data.taikhoannganhang ?: "", highlight = true),
            InfoDetail(stringProvider.getString(R.string.bank_name), data.tennganhang ?: ""),
            InfoDetail(stringProvider.getString(R.string.introducer), data.tennguoigioithieu ?: ""),
            InfoDetail(stringProvider.getString(R.string.care_date), data.ngaychamsoc ?: ""),
            InfoDetail(stringProvider.getString(R.string.care_staff), data.tennguoichamsoc ?: ""),
            InfoDetail(stringProvider.getString(R.string.facebook), data.facebook ?: ""),
            InfoDetail(stringProvider.getString(R.string.zalo), data.zalo ?: ""),
            InfoDetail(stringProvider.getString(R.string.note), data.ghichu ?: "")
        )
    }

    private fun addEditCustomer(id: String, customer: Customer) {
        viewModelScope.launch(dispatcher) {
            _registerState.value = NetworkResponse.Loading
            try {
                val url = if (id != "0") "/ex/api/editkhachhang" else "/ex/api/addkhachhang"
                val result = customerUseCase.invoke(url, customer, currentUserInfo.token)
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