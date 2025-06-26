package com.kotdev.numbersapp.navigation.destination

import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.kotdev.numbersapp.navigation.core.destination.Destination
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
object MainNavigation : Destination

@Serializable
@Parcelize
data class MainNumbersSaved(
    val id: Long? = null
): Parcelable

@Serializable
data object MainDestination : Destination


@Serializable
data class DetailDestination(val saved: MainNumbersSaved) : Destination


val MainArgsType = object : NavType<MainNumbersSaved?>(isNullableAllowed = true) {
    private val taskSerializer = MainNumbersSaved.serializer()

    override fun get(bundle: Bundle, key: String): MainNumbersSaved? {
        return bundle.getString(key)?.let { Json.decodeFromString(taskSerializer, it) }
    }

    override fun parseValue(value: String): MainNumbersSaved? {
        return if (value == "null") null else Json.decodeFromString(
            taskSerializer,
            Uri.decode(value)
        )
    }

    override fun put(bundle: Bundle, key: String, value: MainNumbersSaved?) {
        bundle.putString(key, value?.let { Json.encodeToString(taskSerializer, it) } ?: "null")
    }

    override fun serializeAsValue(value: MainNumbersSaved?): String {
        return value?.let { Uri.encode(Json.encodeToString(taskSerializer, it)) } ?: "null"
    }
}
