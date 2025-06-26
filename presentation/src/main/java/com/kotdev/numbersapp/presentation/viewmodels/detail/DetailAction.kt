package com.kotdev.numbersapp.presentation.viewmodels.detail


sealed class DetailAction {
    data class OpenUrl(val value: String): DetailAction()
    data class Share(val value: String): DetailAction()
}