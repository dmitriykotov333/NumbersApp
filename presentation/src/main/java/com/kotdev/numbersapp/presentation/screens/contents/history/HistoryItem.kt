package com.kotdev.numbersapp.presentation.screens.contents.history

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kotdev.numbersapp.core_ui.R
import com.kotdev.numbersapp.core_ui.enums.color
import com.kotdev.numbersapp.core_ui.theme.FORMULAR
import com.kotdev.numbersapp.core_ui.theme.GOTHIC
import com.kotdev.numbersapp.data.mappers.HistoryUI
import com.kotdev.numbersapp.presentation.viewmodels.main.MainEvent

@Composable
internal fun HistoryItem(
    item: HistoryUI,
    eventHandler: (MainEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(
                BorderStroke(1.dp, Color(0xD90A396E)),
                RoundedCornerShape(16.dp)
            )
            .clickable {
                eventHandler(MainEvent.OpenDetail(item.id))
            }
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .background(item.type.color(), CircleShape)
                    .padding(6.dp),
                text = stringResource(id = item.type.stringId),
                style = TextStyle(
                    textAlign = TextAlign.Start,
                    color = Color.White,
                    fontSize = 15.sp,
                    fontFamily = FORMULAR,
                    fontWeight = FontWeight.Normal
                )
            )
            Spacer(Modifier.width(12.dp))
            Text(
                modifier = Modifier
                    .background(Color(0xFF2B6AD3), CircleShape)
                    .padding(6.dp),
                text = item.numbers,
                style = TextStyle(
                    textAlign = TextAlign.Start,
                    color = Color.White,
                    fontSize = 15.sp,
                    fontFamily = FORMULAR,
                    fontWeight = FontWeight.Normal
                )
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(5f)
            )
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                imageVector = ImageVector.vectorResource(R.drawable.right_arrow),
                contentDescription = "Open detail"
            )
        }
        Spacer(Modifier.height(6.dp))
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(horizontal = 8.dp)
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = item.description,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(
                textAlign = TextAlign.Start,
                color = Color.Black,
                fontSize = 18.sp,
                fontFamily = GOTHIC,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = item.time,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(
                textAlign = TextAlign.Start,
                color = Color.Gray,
                fontSize = 12.sp,
                fontFamily = FORMULAR,
                fontWeight = FontWeight.Normal
            )
        )
    }
}