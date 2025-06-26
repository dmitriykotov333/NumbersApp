package com.kotdev.numbersapp.core.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent

fun Context.share(text: String) {
    val share = Intent.createChooser(Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, text)
        putExtra(Intent.EXTRA_TITLE, "NumbersApp")
        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        type = "text/plain"
    }, null)
    startActivity(share)
}