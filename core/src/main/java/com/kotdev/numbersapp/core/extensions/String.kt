package com.kotdev.numbersapp.core.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun joinStrings(vararg lines: String): String {
    val builder = StringBuilder()
    for ((index, line) in lines.withIndex()) {
        builder.append(line)
        if (index != lines.lastIndex) {
            builder.append("\n")
        }
    }
    return builder.toString()
}


fun Long.formatCreatedAt(): String {
    val date = Date(this)
    val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    return formatter.format(date)
}