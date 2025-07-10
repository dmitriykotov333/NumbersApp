package com.kotdev.numbersapp.presentation.viewmodels.main


sealed class MainEvent {
    data object ClickGetFact : MainEvent()
    data object ClickGetFactRandom : MainEvent()
    data class ToggleFullScreen(val enable: Boolean) : MainEvent()
    data class OpenDetail(val id: Long) : MainEvent()
    data class SelectedType(val text: String) : MainEvent()
    data class ChangeNumber(val value: String) : MainEvent()
    data class ChangeNumberSecond(val value: String) : MainEvent()
}