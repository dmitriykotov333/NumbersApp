package com.kotdev.numbersapp.presentation.viewmodels.main

import com.kotdev.numbersapp.data.mappers.HistoryUI


sealed class MainEvent {
    data object ClickGetFact : MainEvent()
    data object ClickGetFactRandom : MainEvent()
    data object RemoveItems : MainEvent()
    data object Reset : MainEvent()
    data class ToggleFullScreen(val enable: Boolean) : MainEvent()
    data class OpenDetail(val id: Long) : MainEvent()
    data class SelectedType(val text: String) : MainEvent()
    data class SelectedItem(val id: Long) : MainEvent()
    data class ChangeNumber(val value: String) : MainEvent()
    data class ChangeNumberSecond(val value: String) : MainEvent()
}