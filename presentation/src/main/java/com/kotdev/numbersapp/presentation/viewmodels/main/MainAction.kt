package com.kotdev.numbersapp.presentation.viewmodels.main


sealed class MainAction {
    data class ErrorShow(val value: String): MainAction()
    data class Vibrate(val value: Boolean): MainAction()
}