package com.example.trashminder.presentation.createdReminder

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import com.example.trashminder.R
import com.example.trashminder.model.Reminder
import com.example.trashminder.utils.Notifications
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale


class HomeFragment : Fragment() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                HomeScreen()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun HomeScreen() {
        val viewModel = viewModel<HomeScreenViewModel>()
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            val state = rememberLazyListState()
            val context = LocalContext.current
            LazyColumn(horizontalAlignment = Alignment.CenterHorizontally, state = state) {
                items(viewModel.reminderResponse.value?.reminders ?: emptyList()) {
                    CustomItem(reminder = it)

                    CheckNotificationPermission(it, context)
                }
                item {
                    IconButton(onClick = {
                        findNavController().navigate(R.id.newReminderFragment)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add_circle),
                            contentDescription = getString(R.string.add_button_content_description),
                            modifier = Modifier.size(90.dp),
                            tint = Color.LightGray
                        )
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    private fun CheckNotificationPermission(it: Reminder, context: Context) {

        var hasNotificationPermission by remember {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                mutableStateOf(
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED
                )
            } else {
                mutableStateOf(true)
            }
        }

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted ->
                hasNotificationPermission = isGranted
            }
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            SideEffect {
                launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        if (hasNotificationPermission) {
            setNotifications(it, context)
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

    val currentTime = df.format(Calendar.getInstance().time)
    Log.d("Notifications", "date: ${reminder.date}, currentTime: $currentTime")
    val localDateForCurrentTime = LocalDateTime.parse(currentTime, formatter)
    val currentTimeInMills =
        localDateForCurrentTime.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli()

    if (timeInMilliseconds > currentTimeInMills) {
        val delay = timeInMilliseconds - currentTimeInMills
        val timeSec = System.currentTimeMillis() + delay

        Notifications().setRepetitiveAlarm(
            timeSec,
            context,
            timeInMilliseconds.toInt(),
            reminder.type.name,
            reminder.repetition.name
        )
    }
}
