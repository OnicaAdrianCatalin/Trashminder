package com.example.trashminder.presentation.createdReminder

import android.util.Log
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.trashminder.R
import com.example.trashminder.model.ListOfReminders
import com.example.trashminder.presentation.navigation.NavigationGraphs
import com.example.trashminder.utils.Response

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel = viewModel<HomeScreenViewModel>()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val state = rememberLazyListState()
        Reminders(viewModel = viewModel) { list ->
            LazyColumn(horizontalAlignment = Alignment.CenterHorizontally ,state = state) {
                items(list.reminders) {
                    CustomItem(reminder = it)
                }
                item {
                    IconButton(onClick = {
                        navController.navigate(NavigationGraphs.NEWREMINDER)
                    }) {
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
}


@Composable
fun Reminders(
    viewModel: HomeScreenViewModel,
    content: @Composable (reminders: ListOfReminders) -> Unit
) {
    when (val reminderResponse = viewModel.reminderResponse.value) {
        is Response.Loading -> Log.d("TAG", "Books: print")
        is Response.Success -> content(reminderResponse.data)
        is Response.Failure -> print(reminderResponse.e)
    }
}
