package com.kotdev.numbersapp.presentation.screens.contents

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.kotdev.numbersapp.core_ui.R
import com.kotdev.numbersapp.core_ui.enums.TypeRequest
import com.kotdev.numbersapp.core_ui.enums.color
import com.kotdev.numbersapp.core_ui.modifiers.ShimmerColor
import com.kotdev.numbersapp.core_ui.modifiers.bounceClick
import com.kotdev.numbersapp.core_ui.modifiers.noRippleClickable
import com.kotdev.numbersapp.core_ui.modifiers.shimmer
import com.kotdev.numbersapp.core_ui.theme.FORMULAR
import com.kotdev.numbersapp.core_ui.theme.Theme
import com.kotdev.numbersapp.data.mappers.HistoryUI
import com.kotdev.numbersapp.database.history.HistoryDBO
import com.kotdev.numbersapp.database.history.HistoryDao
import com.kotdev.numbersapp.domain.entities.FactData
import com.kotdev.numbersapp.presentation.screens.contents.history.HistoryItem
import com.kotdev.numbersapp.presentation.viewmodels.main.MainEvent
import com.kotdev.numbersapp.presentation.viewmodels.main.MainViewModel
import com.kotdev.numbersapp.presentation.viewmodels.main.MainViewState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.debounce

@Composable
internal fun HistoryContent(
    modifier: Modifier = Modifier,
    histories: LazyPagingItems<HistoryUI>,
    selectedIds: State<Set<Long>>,
    close: Boolean,
    paddingValues: PaddingValues,
    eventHandler: (MainEvent) -> Unit
) {
    var scrollIndex by rememberSaveable {
        mutableStateOf(0)
    }
    var scrollOffset by rememberSaveable {
        mutableStateOf(0)
    }
    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = scrollIndex,
        initialFirstVisibleItemScrollOffset = scrollOffset
    )

    val idsIsNotEmpty by remember {
        derivedStateOf {
            selectedIds.value.isNotEmpty()
        }
    }
    LaunchedEffect(close) {
        if (close) {
            listState.scrollToItem(0)
        }
    }
    LaunchedEffect(Unit) {
        snapshotFlow { histories.loadState.refresh }
            .collect { state ->
                if (state is LoadState.NotLoading) {
                    listState.animateScrollToItem(0)
                }
            }
    }
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex to listState.firstVisibleItemScrollOffset }
            .debounce(500L)
            .collect { (index, offset) ->
                scrollIndex = index
                scrollOffset = offset
            }
    }

    Column(
        modifier = modifier.then(
            Modifier
                .graphicsLayer(translationY = -50f.dp.value)
        ),
        verticalArrangement = Arrangement.Top
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
            state = listState,
            contentPadding = PaddingValues(
                top = 12.dp,
                bottom = paddingValues.calculateBottomPadding(),
                start = 12.dp,
                end = 12.dp
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (idsIsNotEmpty) {
                stickyHeader {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(top = paddingValues.calculateTopPadding() + 20.dp)
                    ) {
                        Button(
                            onClick = {
                                eventHandler(MainEvent.Reset)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xD9061E3A),
                                contentColor = Color.Black
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth(),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.cancel),
                                modifier = Modifier.padding(vertical = 7.dp),
                                style = TextStyle(
                                    textAlign = TextAlign.Center,
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    fontFamily = FORMULAR,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                        Spacer(Modifier.height(8.dp))
                        Button(
                            onClick = {
                                eventHandler(MainEvent.RemoveItems)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xD37E0C37),
                                contentColor = Color.Black
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth(),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.remove, selectedIds.value.size),
                                modifier = Modifier.padding(vertical = 7.dp),
                                style = TextStyle(
                                    textAlign = TextAlign.Center,
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    fontFamily = FORMULAR,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    }
                }
            }
            items(
                count = histories.itemCount,
                key = histories.itemKey(HistoryUI::id),
                contentType = histories.itemContentType { it.type.name }
            ) { index ->
                val paging: HistoryUI = histories[index] ?: return@items
                val isSelected = paging.id in selectedIds.value

                HistoryItem(item = paging.copy(isSelected = isSelected), eventHandler = eventHandler)
            }
            if (histories.loadState.refresh is LoadState.Loading) {
                item {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            } else if (histories.loadState.refresh is LoadState.Error) {
                item {}
            } else if (
                histories.itemCount == 0
            ) {
                item {
                    WaitingContent()
                }
            }
        }
    }
}