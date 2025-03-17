package com.itechpro.domain.model

open class BaseModel(
    open var mamenu: String = "", // ✅ Mặc định rỗng để truyền từ dữ liệu vào
    open var hanhdong: String = "",
    @Transient
    open var device: String = "",
    open val os: String = "Android" // ✅ Chỉ os là mặc định "Android"
)