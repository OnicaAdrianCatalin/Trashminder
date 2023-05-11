package com.example.trashminder.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextField(
    label: String,
    textFieldValue: MutableState<String>,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    Column {
        OutlinedTextField(
            value = textFieldValue.value,
            onValueChange = { textFieldValue.value = it },
            modifier = Modifier.padding(4.dp),
            label = { Text(text = label) },
            shape = RoundedCornerShape(32.dp),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
            visualTransformation = visualTransformation
        )
    }
}

@Composable
fun CustomSnackBar(text: MutableState<String>) {
    Snackbar(elevation = 0.dp, backgroundColor = Color.Transparent) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color.Black),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(text = text.value, modifier = Modifier.padding(start = 10.dp))
        }
    }
}