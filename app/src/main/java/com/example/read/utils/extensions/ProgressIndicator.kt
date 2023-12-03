package com.example.read.utils.extensions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.read.ui.theme.Purple50
import com.example.read.ui.theme.Transparent20

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Transparent20),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Purple50)
    }
}

@Preview
@Composable
fun PreviewLoadingIndicator() {
    LoadingIndicator()
}