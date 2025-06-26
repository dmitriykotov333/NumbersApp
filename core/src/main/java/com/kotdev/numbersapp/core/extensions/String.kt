package com.kotdev.numbersapp.core.extensions

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
