package com.kotdev.numbersapp.presentation.viewmodels.filter

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.kotdev.numbersapp.core.utils.Utils
import com.kotdev.numbersapp.core_ui.enums.TypeRequest
import com.kotdev.numbersapp.data.mappers.HistoryUI
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class FilterViewState(
    val selected: ImmutableList<TypeRequest> = persistentListOf(),
)