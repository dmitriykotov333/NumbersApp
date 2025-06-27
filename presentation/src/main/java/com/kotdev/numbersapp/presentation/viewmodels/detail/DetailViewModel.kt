package com.kotdev.numbersapp.presentation.viewmodels.detail


import androidx.lifecycle.SavedStateHandle
import com.kotdev.numbersapp.core.extensions.joinStrings
import com.kotdev.numbersapp.core.viewmodel.BaseViewModel
import com.kotdev.numbersapp.data.extensions.toTypeRequest
import com.kotdev.numbersapp.database.FactDatabase
import com.kotdev.numbersapp.domain.entities.FactRequest
import com.kotdev.numbersapp.domain.repositories.FactRepository
import com.kotdev.numbersapp.navigation.core.AppNavigator
import com.kotdev.numbersapp.navigation.destination.DetailDestination
import com.kotdev.numbersapp.navigation.destination.MainNumbersSaved
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.internal.toImmutableList
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val navigator: AppNavigator,
    private val db: FactDatabase,
) : BaseViewModel<DetailViewState, DetailAction, DetailEvent>(
    initialState = DetailViewState()
) {

    private val savedJson = savedStateHandle.get<String>("saved") ?: ""
    private val savedId = Json.Default.decodeFromString<MainNumbersSaved>(savedJson)

    init {
        coroutineScope.launch {
            val rst = db.historyDao.getById(savedId.id!!)
            updateState {
                copy(
                    type = rst.type.toTypeRequest(),
                    description = rst.description,
                    number = rst.numbers.toString(),
                    url = ""
                )
            }
        }
    }

    override fun obtainEvent(viewEvent: DetailEvent) {
        when (viewEvent) {
            is DetailEvent.ClickBack -> {
                navigator.onBackClick()
            }

            is DetailEvent.ClickShare -> {
                sendIntent(
                    DetailAction.Share(
                        joinStrings(
                            "http://numbersapi.com/${viewState.number}/${viewState.type.name.lowercase()}",
                            viewState.type.name,
                            viewState.number,
                            viewState.description,
                        )
                    )
                )
            }

            is DetailEvent.OpenUrl -> {
                sendIntent(
                    DetailAction.OpenUrl(
                        "http://numbersapi.com/${viewState.number}/${viewState.type.name.lowercase()}"
                    )
                )
            }
        }
    }
}