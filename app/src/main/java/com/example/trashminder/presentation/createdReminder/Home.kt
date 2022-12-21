package com.example.trashminder.presentation.createdReminder

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.trashminder.repository.ReminderRepository

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val reminderRepository= ReminderRepository()
        val getAllData=reminderRepository.getAllData()

        LazyColumn(){
            items(items=getAllData){ reminder ->
                CustomItem(reminder = reminder)
            }
        }
    }
}

@Composable
@Preview (showBackground = true)
fun HomeScreenPreview(){
    HomeScreen()
}