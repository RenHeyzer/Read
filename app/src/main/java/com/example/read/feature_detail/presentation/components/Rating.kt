package com.example.read.feature_detail.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.StarRate
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.read.R
import com.example.read.ui.theme.Rubik
import com.example.read.ui.theme.Yellow

@Composable
fun Rating(modifier: Modifier = Modifier, rating: String) {
    Row(
        modifier = modifier,
    ) {

        Icon(
            imageVector = Icons.Sharp.StarRate, contentDescription = stringResource(
                id = R.string.rating_icon_content_description
            ),
            tint = Yellow,
            modifier = Modifier.size(20.dp)
        )

        Text(
            text = rating,
            fontSize = 14.sp,
            color = Color.White,
            fontFamily = Rubik,
            fontWeight = FontWeight.Medium
        )
    }
}