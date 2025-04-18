package com.itechpro.domain.usecase.sell

import com.itechpro.domain.model.Category

import javax.inject.Inject

class SellConfigUseCase @Inject constructor(


    ) {



    data class CategoryAffiliateConfig(
        val objApi: String,
        val modeApi: String,
        val type: String,
        val tabs: List<Category>
    )

    private data class ConfigData(
        val objApi: String,
        val modeApi: String,
        val type: String,
        val tabs: List<Category>
    )

    fun generateConfig(
        displayProduct: String,
        displayService: String,
        displayPriority: String
    ): CategoryAffiliateConfig {
        val isProductEnabled = displayProduct == "1"
        val isServiceEnabled = displayService == "1"
        val isProductPriority = displayPriority == "1"

        val (objApi, modeApi, type, tabs) = when {
            isProductEnabled && isServiceEnabled && isProductPriority -> {
                ConfigData(
                    "tatcasp",
                    "modetatcasp",
                    "huuhinh",
                    listOf(Category(code = "huuhinh"), Category(code = "dichvu"))
                )
            }

            isProductEnabled && isServiceEnabled && !isProductPriority -> {
                ConfigData(
                    "tatcadv",
                    "modetatcadv",
                    "dichvu",
                    listOf(Category(code = "dichvu"), Category(code = "huuhinh"))
                )
            }

            isProductEnabled -> {
                ConfigData(
                    "tatcasp",
                    "modetatcasp",
                    "huuhinh",
                    listOf(Category(code = "product"))
                )
            }

            isServiceEnabled -> {
                ConfigData(
                    "tatcadv",
                    "modetatcadv",
                    "dichvu",
                    listOf(Category(code = "service"))
                )
            }

            else -> ConfigData("", "", "", emptyList())
        }

        return CategoryAffiliateConfig(
            objApi = objApi,
            modeApi = modeApi,
            type = type,
            tabs = tabs
        )
    }



}
