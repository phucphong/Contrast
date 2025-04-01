package com.itechpro.domain.usecase.catogory

import com.itechpro.domain.model.Category
class FilterCategoryUseCase {
    operator fun invoke(categories: List<Category>, query: String?): List<Category> {
        if (query.isNullOrBlank()) return categories

        val lowerQuery = query.lowercase()
        return categories.filter {
            it.name.contains(lowerQuery, ignoreCase = true) ||
                    (it.code?.contains(lowerQuery, ignoreCase = true) == true) ||
                    (it.description?.contains(lowerQuery, ignoreCase = true) == true)
        }
    }
}
