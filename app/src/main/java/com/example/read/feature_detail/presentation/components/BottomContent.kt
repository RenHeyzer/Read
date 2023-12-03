package com.example.read.feature_detail.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.read.R
import com.example.read.ui.theme.PurpleHorizontal
import com.example.read.ui.theme.PurpleVerticalToBottom
import com.example.read.ui.theme.Rubik
import com.example.read.ui.theme.WhiteVertical

@Composable
fun BottomContent(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
    ) {
        Button(
            modifier = Modifier
                .fillMaxHeight()
                .width(0.dp)
                .weight(0.8f),
            contentPadding = PaddingValues(),
            shape = RoundedCornerShape(
                topStart = 10.dp,
                bottomStart = 10.dp,
                topEnd = 0.dp,
                bottomEnd = 0.dp
            ),
            border = BorderStroke(2.dp, brush = PurpleHorizontal),
            colors = ButtonDefaults.buttonColors(contentColor = Color.Transparent),
            onClick = {}
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(WhiteVertical),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.button_chapters),
                    fontSize = 10.sp,
                    color = Color.Black,
                    fontFamily = Rubik,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }

        Button(
            modifier = Modifier
                .fillMaxHeight()
                .width(0.dp)
                .weight(1f),
            contentPadding = PaddingValues(),
            shape = RoundedCornerShape(
                topStart = 0.dp,
                bottomStart = 0.dp,
                topEnd = 10.dp,
                bottomEnd = 10.dp
            ),
            border = BorderStroke(2.dp, brush = PurpleHorizontal),
            colors = ButtonDefaults.buttonColors(contentColor = Color.Transparent),
            onClick = {}
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(PurpleVerticalToBottom),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.button_read),
                    fontSize = 10.sp,
                    color = Color.White,
                    fontFamily = Rubik,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}