package com.example.trashminder.presentation.createdReminder

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.trashminder.R
import com.example.trashminder.model.ListOfReminders
import com.example.trashminder.model.Reminder
import com.example.trashminder.presentation.navigation.NavigationGraphs
import com.example.trashminder.utils.Response
import com.example.trashminder.utils.TrashminderNotifications
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(navController: NavController) {

    val viewModel = viewModel<HomeScreenViewModel>()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val context= LocalContext.current
        val state = rememberLazyListState()
        Reminders(viewModel = viewModel) { list ->
            LazyColumn(horizontalAlignment = Alignment.CenterHorizontally, state = state) {
                items(list.reminders) {
                    CustomItem(reminder = it)

                    setNotifications(it, context)
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

@RequiresApi(Build.VERSION_CODES.O)
fun setNotifications(reminder: Reminder, context: Context) {

    val formatter = DateTimeFormatter.ofPattern(
        "dd/MM/yyyy HH:mm",
        Locale.ENGLISH
    )
    val df = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH)

    val localDateForTime = LocalDateTime.parse(reminder.date, formatter)
    val timeInMilliseconds =
        localDateForTime.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli()

    var currentTime = df.format(Calendar.getInstance().time)
    Log.d("Main", "date: ${reminder.date}, currentTime: $currentTime")
    var localDateForCurrentTime = LocalDateTime.parse(currentTime, formatter)
    var currentTimeInMills =
        localDateForCurrentTime.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli()

    if(timeInMilliseconds>currentTimeInMills){
        val delay = timeInMilliseconds - currentTimeInMills
        val timeSec=System.currentTimeMillis()+delay

        Log.d("Main", "Id to int= ${timeInMilliseconds.toInt()}")

        TrashminderNotifications().setRepetitiveAlarm(timeSec,context,timeInMilliseconds.toInt(), reminder.type, reminder.repetition)
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
