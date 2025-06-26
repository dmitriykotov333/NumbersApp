package com.kotdev.numbersapp.presentation.viewmodels.filter

import com.kotdev.numbersapp.core.utils.Utils
import com.kotdev.numbersapp.core.viewmodel.BaseViewModel
import com.kotdev.numbersapp.core_ui.enums.TypeRequest
import com.kotdev.numbersapp.data.datastore.FilterPreferences
import com.kotdev.numbersapp.data.extensions.toTypeRequest
import com.kotdev.numbersapp.data.mappers.mapToFactRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val filterPrefs: FilterPreferences
) : BaseViewModel<FilterViewState, Nothing, FilterEvent>(
    initialState = FilterViewState()
) {

    init {
        coroutineScope.launch {
            val selected = filterPrefs.getFirst().map {
                it.toTypeRequest()
            }.toImmutableList()
            if (selected.isNotEmpty()) {
                updateState {
                    copy(selected = selected)
                }
            } else {
                updateState {
                    copy(selected = persistentListOf(
                        TypeRequest.MATH,
                        TypeRequest.TRIVIA,
                        TypeRequest.YEAR,
                        TypeRequest.DATE
                    ))
                }
            }
        }
    }

    override fun obtainEvent(viewEvent: FilterEvent) {
        when (viewEvent) {
            is FilterEvent.SelectedType -> {
                coroutineScope.launch {
                    val selectedTypes: MutableList<TypeRequest> = viewState.selected.toMutableList()
                    val isChecked = selectedTypes.contains(viewEvent.type)

                    if (isChecked) selectedTypes.remove(viewEvent.type)
                    else selectedTypes.add(viewEvent.type)

                    filterPrefs.saveSelectedTypes(selectedTypes.map {
                        it.name
                    }.toSet())
                    updateState {
                        copy(
                            selected = selectedTypes.toImmutableList()
                        )
                    }
                }

            }
        }
    }
}