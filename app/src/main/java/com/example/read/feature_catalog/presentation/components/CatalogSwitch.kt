package com.example.read.feature_catalog.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.read.R
import com.example.read.ui.theme.Purple60
import com.example.read.ui.theme.PurpleHorizontal
import com.example.read.ui.theme.PurpleVerticalToBottom
import com.example.read.ui.theme.Rubik
import com.example.read.ui.theme.WhiteVertical

@Composable
fun CatalogSwitch(
    modifier: Modifier = Modifier,
    checkedCatalog: (checked: Boolean) -> Unit = {},
    checked: Boolean = false
) {

    Row(modifier) {
        Button(
            modifier = Modifier
                .fillMaxHeight()
                .width(0.dp)
                .weight(1f),
            contentPadding = PaddingValues(),
            shape = RoundedCornerShape(
                topStart = 10.dp,
                bottomStart = 10.dp,
                topEnd = 0.dp,
                bottomEnd = 0.dp
            ),
            border = BorderStroke(2.dp, Purple60),
            colors = ButtonDefaults.buttonColors(contentColor = Color.Transparent),
            onClick = {
                checkedCatalog(false)
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(if (!checked) PurpleVerticalToBottom else WhiteVertical),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.comics_catalog),
                    fontSize = 16.sp,
                    color = if (!checked) Color.White else Color.Black,
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
            border = BorderStroke(2.dp, Purple60),
            colors = ButtonDefaults.buttonColors(contentColor = Color.Transparent),
            onClick = {
                checkedCatalog(true)
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(if (checked) PurpleVerticalToBottom else WhiteVertical),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.manga_catalog),
                    fontSize = 16.sp,
                    color = if (checked) Color.White else Color.Black,
                    fontFamily = Rubik,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewCatalogSwitch() {
    CatalogSwitch(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
    )
}