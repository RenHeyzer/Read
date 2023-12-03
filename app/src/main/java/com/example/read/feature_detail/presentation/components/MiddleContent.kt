package com.example.read.feature_detail.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.read.feature_detail.domain.models.Info
import com.example.read.ui.theme.PurpleHorizontal
import com.example.read.ui.theme.PurpleVerticalToBottom
import com.example.read.ui.theme.Rubik

@Composable
fun MiddleContent(modifier: Modifier = Modifier, info: Info) = with(info) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(2.dp, brush = PurpleHorizontal)
    ) {

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 20.dp)
                .border(
                    BorderStroke(2.dp, brush = PurpleHorizontal),
                    RoundedCornerShape(10.dp)
                )
                .clip(RoundedCornerShape(10.dp))
                .background(PurpleVerticalToBottom, RoundedCornerShape(10.dp)),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
            content = {
                genres?.let {
                    items(
                        it
                    ) { genre ->
                        Text(
                            text = genre,
                            fontSize = 12.sp,
                            color = Color.White,
                            fontFamily = Rubik,
                            fontWeight = FontWeight.Normal
                        )
                        if (genre != it.last()) {
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                    }
                }
            })

        description?.let {
            val state = rememberScrollState()
            LaunchedEffect(Unit) { state.animateScrollTo(100) }
            Text(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp).verticalScroll(state),
                text = it,
                fontSize = 14.sp,
                color = Color.Black,
                fontFamily = Rubik,
                fontWeight = FontWeight.Normal
            )
        }
    }
}