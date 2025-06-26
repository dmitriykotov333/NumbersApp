package com.kotdev.numbersapp.domain.entities

data class History(
    val id: Long,
    val numbers: String,
    val type: String,
    val description: String
)