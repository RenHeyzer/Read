package com.example.read.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val PurpleVerticalToBottom = Brush.verticalGradient(
    listOf(Purple50, Purple60, Purple70)
)

val PurpleVerticalToTop = Brush.verticalGradient(
    listOf(Purple70, Purple60, Purple50)
)

val PurpleHorizontal = Brush.horizontalGradient(
    listOf(Purple50, Purple60),
)

val WhiteVertical = Brush.verticalGradient(
    listOf(
        Color.White,
        LightGray
    )
)