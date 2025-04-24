package com.itechpro.domain.usecase.dowloadFile



interface DownloadImageUseCase {
    operator fun invoke(url: String, fileName: String)
}
