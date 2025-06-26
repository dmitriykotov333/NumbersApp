package com.kotdev.numbersapp.presentation.viewmodels.detail

import android.content.Context
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.kotdev.numbersapp.core.api.StatusRequest
import com.kotdev.numbersapp.core.api.wrapper
import com.kotdev.numbersapp.core.extensions.joinStrings
import com.kotdev.numbersapp.core.extensions.openUrl
import com.kotdev.numbersapp.core.extensions.share
import com.kotdev.numbersapp.core.viewmodel.BaseViewModel
import com.kotdev.numbersapp.core_ui.enums.TypeRequest
import com.kotdev.numbersapp.data.FactRandomRepository
import com.kotdev.numbersapp.data.extensions.toTypeRequest
import com.kotdev.numbersapp.data.mappers.HistoryUI
import com.kotdev.numbersapp.data.mappers.mapToFactRequest
import com.kotdev.numbersapp.data.mappers.mapToFactRequestRandom
import com.kotdev.numbersapp.database.FactDatabase
import com.kotdev.numbersapp.domain.entities.FactRequest
import com.kotdev.numbersapp.domain.repositories.FactRepository
import com.kotdev.numbersapp.navigation.core.AppNavigator
import com.kotdev.numbersapp.navigation.destination.DetailDestination
import com.kotdev.numbersapp.navigation.destination.MainNumbersSaved
import com.kotdev.numbersapp.presentation.viewmodels.main.MainAction
import com.kotdev.numbersapp.presentation.viewmodels.main.MainEvent
import com.kotdev.numbersapp.presentation.viewmodels.main.MainViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.internal.toImmutableList
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class DetailViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
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