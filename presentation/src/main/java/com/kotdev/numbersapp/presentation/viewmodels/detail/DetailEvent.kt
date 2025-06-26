package com.kotdev.numbersapp.presentation.viewmodels.detail


sealed class DetailEvent {
    data object ClickBack : DetailEvent()
    data object ClickShare : DetailEvent()
    data object OpenUrl : DetailEvent()
}