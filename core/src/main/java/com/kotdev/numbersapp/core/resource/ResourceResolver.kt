package com.kotdev.numbersapp.core.resource

import android.content.Context

interface ResourceResolver {

    fun <T : Any> resolve(resource: Resource<T>): T
}

