package com.example.trashminder.presentation.createdReminder

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.trashminder.R
import com.example.trashminder.repository.ReminderRepository

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val reminderRepository = ReminderRepository()
        val getAllData = reminderRepository.getAllData()
        val state = rememberLazyListState()

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally, state = state
        ) {
            items(items = getAllData) { reminder ->
                CustomItem(reminder = reminder)
            }
            item {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add_circle),
                        contentDescription = "Add button",
                        modifier = Modifier.size(90.dp),
                        tint = Color.LightGray
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    HomeScreen()
}