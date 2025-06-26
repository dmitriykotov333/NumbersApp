package com.kotdev.numbersapp.presentation.viewmodels.detail

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.toArgb
import com.kotdev.numbersapp.core_ui.R
import com.kotdev.numbersapp.core_ui.enums.TypeRequest
import com.kotdev.numbersapp.core_ui.theme.AppTheme
import com.kotdev.numbersapp.core_ui.theme.Theme
import com.kotdev.numbersapp.data.mappers.HistoryUI
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf

data class DetailViewState(
    val type: TypeRequest = TypeRequest.MATH,
    val description: String = "",
    val number: String = "",
    val url: String = "",
)