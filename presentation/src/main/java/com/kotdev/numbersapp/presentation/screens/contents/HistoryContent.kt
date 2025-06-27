package com.kotdev.numbersapp.presentation.screens.contents

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.kotdev.numbersapp.core_ui.modifiers.bounceClick
import com.kotdev.numbersapp.core_ui.modifiers.noRippleClickable
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

@Composable
internal fun HistoryContent(
    modifier: Modifier = Modifier,
    histories: LazyPagingItems<HistoryUI>,
    refreshTrigger: Boolean,
    paddingValues: PaddingValues,
    eventHandler: (MainEvent) -> Unit
) {
    val listState = rememberLazyListState()
    LaunchedEffect(refreshTrigger) {
        listState.animateScrollToItem(0)
    }
    LaunchedEffect(histories.itemCount) {
        listState.animateScrollToItem(0)
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
            items(
                count = histories.itemCount,
                key = histories.itemKey(HistoryUI::id),
                contentType = histories.itemContentType { it.type.name }
            ) { index ->
                val paging: HistoryUI = histories[index] ?: return@items
                HistoryItem(item = paging, eventHandler = eventHandler)
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