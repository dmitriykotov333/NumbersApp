package com.kotdev.numbersapp.navigation.core.destination
import kotlinx.serialization.Polymorphic

@Polymorphic
interface Destination  {
    fun getDestination(): String = javaClass.name
}
