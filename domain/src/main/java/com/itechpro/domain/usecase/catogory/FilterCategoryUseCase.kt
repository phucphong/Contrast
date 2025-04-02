package com.itechpro.domain.usecase.catogory

import com.itechpro.domain.model.Category
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class FilterCategoryUseCase {
    operator fun invoke(
        categoriesFlow: StateFlow<List<Category>>,
        query: String?
    ): StateFlow<List<Category>> {
        return categoriesFlow
            .map { list ->
                if (query.isNullOrBlank()) return@map list
                val lowerQuery = query.lowercase()

                list.filter {
                    it.name?.contains(lowerQuery, ignoreCase = true) == true ||
                            it.code?.contains(lowerQuery, ignoreCase = true) == true ||
                            it.description?.contains(lowerQuery, ignoreCase = true) == true
                }
            }
            .stateIn(
                scope = CoroutineScope(Dispatchers.Default), // nên truyền từ ViewModel
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
    }
}