package com.kotdev.numbersapp.presentation.viewmodels.main

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kotdev.numbersapp.core.api.StatusRequest
import com.kotdev.numbersapp.core.api.wrapper
import com.kotdev.numbersapp.core.resource.Resource
import com.kotdev.numbersapp.core.resource.ResourceResolver
import com.kotdev.numbersapp.core.viewmodel.BaseViewModel
import com.kotdev.numbersapp.core_ui.R
import com.kotdev.numbersapp.core_ui.enums.TypeRequest
import com.kotdev.numbersapp.data.FactRandomRepository
import com.kotdev.numbersapp.data.datastore.FilterPreferences
import com.kotdev.numbersapp.data.extensions.toTypeRequest
import com.kotdev.numbersapp.data.mappers.HistoryUI
import com.kotdev.numbersapp.data.mappers.mapToFactRequest
import com.kotdev.numbersapp.data.mappers.mapToFactRequestRandom
import com.kotdev.numbersapp.data.usecase.GetHistoriesUseCase
import com.kotdev.numbersapp.database.FactDatabase
import com.kotdev.numbersapp.domain.entities.FactRequest
import com.kotdev.numbersapp.domain.repositories.FactRepository
import com.kotdev.numbersapp.navigation.core.AppNavigator
import com.kotdev.numbersapp.navigation.destination.DetailDestination
import com.kotdev.numbersapp.navigation.destination.MainNumbersSaved
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.launch
import okhttp3.internal.toImmutableList
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MainViewModel @Inject constructor(
    private val navigator: AppNavigator,
    private val repository: FactRepository,
    private val random: FactRandomRepository,
    private val db: FactDatabase,
    private val filterPrefs: FilterPreferences,
    private val getHistoriesUseCase: GetHistoriesUseCase,
    private val resorcResolver: ResourceResolver
) : BaseViewModel<MainViewState, MainAction, MainEvent>(
    initialState = MainViewState()
) {

    private val firstMap: HashMap<TypeRequest, String> = hashMapOf()

    private val selectedTypes = filterPrefs.getSelectedTypes()
        .stateIn(coroutineScope, SharingStarted.Eagerly, emptySet())

    @OptIn(ExperimentalCoroutinesApi::class)
    val histories: Flow<PagingData<HistoryUI>> = selectedTypes
        .flatMapLatest { selected ->
            getHistoriesUseCase(selected.map {
                it.toTypeRequest()
            }.toSet())
        }
        .cachedIn(coroutineScope)

    init {
        repository.status.onEach {
            when (it.response) {
                is StatusRequest.Success -> {
                    if (it.isRandom) {
                        sendIntent(MainAction.Vibrate(false))
                    }
                    updateState {
                        copy(
                            refreshTrigger = !viewState.refreshTrigger,
                            count = it.response.data?.number ?: 0,
                            isSending = false,
                            isSendingRandom = false
                        )
                    }
                }

                is StatusRequest.Error -> {
                    if (it.isRandom) {
                        sendIntent(MainAction.Vibrate(false))
                    }
                    sendIntent(MainAction.ErrorShow(it.response.msg.toString()))
                    updateState {
                        copy(
                            error = viewState.error.copy(
                                isError = true, text = it.response.msg.toString()
                            ), isSending = false,
                            isSendingRandom = false
                        )
                    }
                }

                is StatusRequest.InProgress -> {
                    if (it.isRandom) {
                        sendIntent(MainAction.Vibrate(true))
                    }
                    updateState {
                        copy(
                            error = viewState.error.copy(
                                isError = false
                            ), isSending = if (it.isRandom) false else true,
                            isSendingRandom = if (it.isRandom) true else false
                        )
                    }
                }
            }
        }.launchIn(coroutineScope)

        coroutineScope.launch {
            random.firstMatch().collectLatest {
                it.data?.forEach {
                    firstMap[it.type.uppercase().toTypeRequest()] = it.description
                    updateState {
                        copy(
                            description = firstMap.getValue(TypeRequest.MATH)
                        )
                    }
                }
            }
        }
    }

    private var job: Job? = null

    override fun obtainEvent(viewEvent: MainEvent) {
        when (viewEvent) {
            is MainEvent.Refresh -> {
                updateState {
                    copy(
                        refreshTrigger = !viewState.refreshTrigger
                    )
                }
            }

            is MainEvent.ClickGetFact -> {
                val number = viewState.number
                val numberSecond = viewState.numberSecond
                val isDateRequest = viewState.selectedRequest == TypeRequest.DATE

                val isValid = if (isDateRequest) {
                    number.isNotBlank() && numberSecond.isNotBlank()
                } else {
                    number.isNotBlank()
                }

                if (!isValid) {
                    sendIntent(MainAction.ErrorShow(resorcResolver.resolve(Resource.String(R.string.empty_field))))
                    return
                }

                val request = viewState.selectedRequest.mapToFactRequest(
                    number.toInt(),
                    if (isDateRequest) numberSecond.toInt() else 0
                )

                launchWrapper {
                    repository.getFact(isRandom = false, request = request)
                }
            }

            is MainEvent.ClickGetFactRandom -> {
                launchWrapper {
                    sendIntent(MainAction.Vibrate(true))
                    repository.getFact(
                        isRandom = true,
                        request = viewState.selectedRequest.mapToFactRequestRandom()
                    )
                }
            }

            is MainEvent.ChangeNumber -> {
                val input = viewEvent.value
                val isDateRequest = viewState.selectedRequest == TypeRequest.DATE

                val isValid = try {
                    if (isDateRequest) input.toIntOrNull()?.let { it in 1..12 } == true
                    else input.length <= 4
                } catch (t: Throwable) {
                    false
                }

                if (isValid) {
                    val newErrorState =
                        if (viewState.error.isError) viewState.error.copy(isError = false) else viewState.error
                    updateState {
                        copy(
                            number = input,
                            error = newErrorState
                        )
                    }
                } else {
                    updateState { copy(number = "") }
                }
            }

            is MainEvent.ChangeNumberSecond -> {
                val input = viewEvent.value
                val isValid = input.toIntOrNull()?.let { it in 1..31 } == true

                if (isValid) {
                    val newErrorState = if (viewState.error.isError) viewState.error.copy(isError = false) else viewState.error
                    updateState {
                        copy(
                            numberSecond = input,
                            error = newErrorState
                        )
                    }
                } else {
                    updateState { copy(numberSecond = "") }
                }
            }

            is MainEvent.SelectedType -> {
                updateState {
                    copy(
                        selectedRequest = viewEvent.text.toTypeRequest(),
                        description = firstMap.getValue(viewEvent.text.toTypeRequest())
                    )
                }
            }

            is MainEvent.OpenDetail -> {
                navigator.push(
                    DetailDestination(
                        MainNumbersSaved(viewEvent.id)
                    )
                )
            }

            is MainEvent.ToggleFullScreen -> {
                updateState {
                    copy(fullScreen = viewEvent.enable)
                }
            }
        }
    }

    private fun launchWrapper(
        block: suspend () -> Unit
    ) {
        if (job != null) {
            sendIntent(MainAction.ErrorShow(
                resorcResolver.resolve(Resource.String(R.string.request_progress))))
            return
        }
        job = coroutineScope.launch {
            block()
        }.also {
            it.invokeOnCompletion {
                job = null
            }
        }
    }
}