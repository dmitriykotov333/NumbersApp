package com.kotdev.numbersapp.presentation.screens.contents

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kotdev.numbersapp.core.utils.Utils
import com.kotdev.numbersapp.core_ui.R
import com.kotdev.numbersapp.core_ui.enums.TypeRequest
import com.kotdev.numbersapp.core_ui.theme.FORMULAR
import com.kotdev.numbersapp.core_ui.theme.GOTHIC
import com.kotdev.numbersapp.presentation.viewmodels.filter.FilterEvent
import com.kotdev.numbersapp.presentation.viewmodels.filter.FilterViewModel

@Composable
fun FilterCheckboxList(
    viewModel: FilterViewModel = hiltViewModel<FilterViewModel>(),
    onClick: () -> Unit,
) {
    val state by viewModel.states().collectAsState()
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        IconButton(onClick = onClick) {
            Icon(Icons.Default.Close, "")
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    viewModel.obtainEvent(FilterEvent.SelectedSort(!state.isDescending))
                }
                .padding(8.dp)
        ) {
            Checkbox(
                checked = state.isDescending,
                onCheckedChange = {
                    viewModel.obtainEvent(FilterEvent.SelectedSort(!state.isDescending))
                }
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.descending), style = TextStyle(
                    textAlign = TextAlign.Start,
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontFamily = FORMULAR,
                    fontWeight = FontWeight.Medium
                )
            )
        }
        Spacer(Modifier.height(12.dp))
        Utils.filter.forEach { type ->
            key(
                type
            ) {
                val selectedTypes = state.selected
                val isChecked = selectedTypes.contains(type)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (isChecked && selectedTypes.size == 1) return@clickable
                            viewModel.obtainEvent(FilterEvent.SelectedType(type))

                        }
                        .padding(8.dp)
                ) {
                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = {
                            if (it) {
                                viewModel.obtainEvent(FilterEvent.SelectedType(type))
                            } else {
                                if (selectedTypes.size == 1) return@Checkbox
                                viewModel.obtainEvent(FilterEvent.SelectedType(type))
                            }
                        }
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = type.name, style = TextStyle(
                            textAlign = TextAlign.Start,
                            color = Color.Black,
                            fontSize = 18.sp,
                            fontFamily = FORMULAR,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }
        }
    }
}