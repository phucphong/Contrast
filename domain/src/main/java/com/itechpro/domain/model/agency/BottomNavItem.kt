package com.itechpro.domain.model.agency


data class BottomNavItem(
    val label: String,
    val icon: Int,
    val hasLabel: Boolean,
    val isCenter: Boolean = false
)
