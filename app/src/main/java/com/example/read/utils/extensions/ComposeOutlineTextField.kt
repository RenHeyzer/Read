package com.example.read.utils.extensions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.read.ui.theme.Purple60

@Composable
fun OutlinedErrorTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    errorText: String,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null
) {
    Column(modifier) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Purple60,
                focusedBorderColor = Purple60,
                unfocusedLabelColor = if (value.isEmpty()) Color.LightGray else Purple60,
                focusedLabelColor = Purple60,
                errorBorderColor = Color.Red,
                errorLabelColor = Color.Red,
            ),
            shape = RoundedCornerShape(10.dp),
            isError = isError,
            label = label,
            placeholder = placeholder
        )
        if (isError) {
            Text(modifier = Modifier.padding(start = 10.dp),text = errorText, color = Color.Red)
        }
    }
}