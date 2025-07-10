package com.kotdev.numbersapp.presentation.viewmodels.main

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

data class MainViewState(
    val count: Int = 0,
    val fullScreen: Boolean = false,
    val selectedRequest: TypeRequest = TypeRequest.MATH,
    val description: String = "",
    val number: String = "",
    val numberSecond: String = "",
    val histories: ImmutableList<HistoryUI> = persistentListOf(),
    val isSending: Boolean = false,
    val isSendingRandom: Boolean = false,
    val isLoading: Boolean = false,
    val error: EditTextError = EditTextError(isError = false, text = "")
)

@Immutable
data class EditTextError(
    val isError: Boolean = false,
    val text: String
)