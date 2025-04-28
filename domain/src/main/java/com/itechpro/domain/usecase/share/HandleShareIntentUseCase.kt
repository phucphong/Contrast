package com.itechpro.domain.usecase.share

import android.content.Intent
import com.itechpro.domain.model.share.ShareResult
import javax.inject.Inject

class HandleShareIntentUseCase @Inject constructor() {
    fun execute(intent: Intent): ShareResult? {
        val data = intent.data ?: return null

        val id = data.getQueryParameter("id").orEmpty()
        val idUnit = data.getQueryParameter("iddonvi").orEmpty()
        val domain = data.getQueryParameter("domain").orEmpty()
        val introducerId = data.getQueryParameter("idngt").orEmpty()

        if (id.isEmpty() || idUnit.isEmpty()) return null

        return ShareResult(id, idUnit,introducerId, domain)
    }
}