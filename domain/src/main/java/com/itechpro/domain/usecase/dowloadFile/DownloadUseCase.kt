package com.itechpro.domain.usecase.dowloadFile



interface DownloadUseCase {
    operator fun invoke(url: String, fileName: String)
}
