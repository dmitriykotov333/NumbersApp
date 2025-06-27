package com.kotdev.numbersapp.core.resource

import androidx.annotation.StringRes

sealed interface Resource<out T : Any> {

    val resId: Int

    @JvmInline
    value class String(@StringRes override val resId: Int) : Resource<kotlin.String>
}