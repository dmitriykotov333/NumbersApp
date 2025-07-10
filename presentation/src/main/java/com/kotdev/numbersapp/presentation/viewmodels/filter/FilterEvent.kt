package com.kotdev.numbersapp.presentation.viewmodels.filter

import com.kotdev.numbersapp.core_ui.enums.TypeRequest


sealed class FilterEvent {
    data class SelectedType(val type: TypeRequest) : FilterEvent()
    data class SelectedSort(val isDescending: Boolean) : FilterEvent()
}