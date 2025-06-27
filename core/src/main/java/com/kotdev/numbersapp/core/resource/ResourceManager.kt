package com.kotdev.numbersapp.core.resource

import android.content.Context

class ResourceManager(
    private val context: Context,
) : ResourceResolver {
    override fun <T : Any> resolve(resource: Resource<T>): T = when (resource) {
        is Resource.String -> context.getString(resource.resId) as T
    }
}