package com.example.trashminder.presentation.newReminder

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import com.example.trashminder.R
import com.example.trashminder.presentation.theme.black
import com.example.trashminder.presentation.theme.bleu
import com.example.trashminder.presentation.theme.darkerGreen
import com.example.trashminder.presentation.theme.lightBlack
import com.example.trashminder.presentation.theme.lightGreen
import com.example.trashminder.utils.TimePeriod
import com.example.trashminder.utils.TrashType
import com.example.trashminder.utils.toResourceId
import java.util.Calendar
import java.util.Date
import kotlinx.coroutines.launch

class NewReminderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {
                NewReminderScreen()
            }
        }
    }

    @Composable
    fun NewReminderScreen() {
        val viewModel = viewModel<NewReminderViewModel>()
        val expanded = remember { mutableStateOf(false) }
        val dateAndTimeDialogTrigger = remember { mutableStateOf(false) }
        val chosenDate = remember { mutableStateOf(getString(R.string.date_and_time_button)) }
        val timespan = remember { mutableStateOf<Int?>(null) }
        val trashType = remember { mutableStateOf(TrashType.Plastic) }
        val scaffoldState = rememberScaffoldState()
        val coroutineScope = rememberCoroutineScope()
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(
                    backgroundColor = Color.Transparent,
                    elevation = 0.dp,
                    modifier = Modifier.background(
                        brush = Brush.verticalGradient(
                            listOf(lightGreen, darkerGreen)
                        )
                    )
                ) {}
            },
            bottomBar = {
                BottomAppBar(
                    backgroundColor = Color.Transparent,
                    elevation = 0.dp,
                    modifier = Modifier.background(
                        brush = Brush.verticalGradient(
                            listOf(darkerGreen, bleu)
                        )
                    )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        GradientButton(
                            modifier = Modifier.size(width = 160.dp, height = 40.dp),
                            text = getString(R.string.save_button)
                        ) {
                            coroutineScope.launch {
                                if (timespan.value == null ||
                                    chosenDate.value == getString(R.string.date_and_time_button)
                                ) {
                                    scaffoldState.snackbarHostState.showSnackbar(
                                        message = getString(R.string.fields_cannot_be_not_empty_validation),
                                        actionLabel = getString(R.string.error)
                                    )
                                } else {
                                    viewModel.createProfileOrAddData(
                                        type = trashType.value,
                                        date = chosenDate.value,
                                        repetition = TimePeriod.values()[timespan.value!!]
                                    )
                                    findNavController().navigate(R.id.action_newReminderFragment_to_homeFragment)
                                }
                            }
                        }
                    }
                }
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = getString(R.string.choose_trash_type),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(12.dp)
                    )
                    DropDownButton(trashType, expanded)
                    GradientButton(
                        modifier = Modifier
                            .padding(12.dp)
                            .size(width = 250.dp, height = 40.dp),
                        text = chosenDate.value
                    ) {
                        dateAndTimeDialogTrigger.value = true
                    }
                    if (dateAndTimeDialogTrigger.value) {
                        PickDateAndTime(chosenDate)
                        dateAndTimeDialogTrigger.value = false
                    }
                    TimePeriodPick(timespan)
                    Button(onClick = { }) {
                        Text(text = getString(R.string.save_button))
                    }
                }
            }
        }
    }

    @Composable
    private fun TimePeriodPick(timespan: MutableState<Int?>) {
        Surface(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp)),
            border = BorderStroke(
                1.dp,
                Brush.verticalGradient(
                    listOf(lightGreen, darkerGreen)
                )
            )
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = getString(R.string.repetition),
                    modifier = Modifier.padding(8.dp),
                    fontWeight = FontWeight.Bold
                )
                LazyColumn(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .padding(start = 30.dp)
                        .fillMaxWidth()
                ) {
                    items(count = TimePeriod.values().size) { item ->
                        Row(modifier = Modifier.padding(12.dp)) {
                            RadioButton(
                                selected = timespan.value == item,
                                modifier = Modifier.size(20.dp),
                                onClick = {
                                    timespan.value = item
                                }
                            )
                            Text(
                                text = stringResource(id = TimePeriod.values()[item].toResourceId()),
                                modifier = Modifier.padding(start = 6.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun PickDateAndTime(chosenDate: MutableState<String>) {
        val context = LocalContext.current
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minutes = calendar.get(Calendar.MINUTE)
        var date = ""
        var time = ""
        calendar.time = Date()

        val datePickerDialog = DatePickerDialog(
            context,
            { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
                date =
                    setDateCorrectFormat(selectedMonth, selectedDayOfMonth, selectedYear)
            }, year, month, day
        )
        val timePickerDialog = TimePickerDialog(
            context,
            { _, selectedHour: Int, selectedMinute: Int ->
                time = setTimeCorrectFormat(selectedHour, selectedMinute)
            }, hour, minutes, false
        )
        datePickerDialog.show()
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.setOnDismissListener {
            if (date != "") {
                timePickerDialog.show()
            }
        }

        timePickerDialog.setOnDismissListener {
            if (time != "") {
                chosenDate.value = date + time
            }
        }
    }

    private fun setDateCorrectFormat(
        selectedMonth: Int,
        selectedDayOfMonth: Int,
        selectedYear: Int
    ): String {
        var newDay = "$selectedDayOfMonth"
        var newMonth = "${selectedMonth + 1}"
        if (selectedDayOfMonth < 10) {
            newDay = "0$selectedDayOfMonth"
        }
        if (selectedMonth < 10) {
            newMonth = "0${selectedMonth + 1}"
        }

        return "$newDay/$newMonth/$selectedYear"
    }

    private fun setTimeCorrectFormat(selectedHour: Int, selectedMinute: Int): String {
        var newHour = "$selectedHour"
        var newMinutes = "$selectedMinute"

        if (selectedHour < 10) {
            newHour = "0$selectedHour"
        }
        if (selectedMinute < 10) {
            newMinutes = "0$selectedMinute"
        }

        return " $newHour:$newMinutes"
    }

    @Composable
    private fun DropDownButton(
        trashType: MutableState<TrashType>,
        expanded: MutableState<Boolean>
    ) {
        Column {
            OutlinedTextField(
                value = stringResource(id = trashType.value.toResourceId()), onValueChange = { },
                modifier = Modifier
                    .padding(12.dp)
                    .size(width = 250.dp, height = 55.dp)
                    .clickable {
                        expanded.value = true
                    },
                enabled = false,
                colors = TextFieldDefaults.outlinedTextFieldColors(disabledTextColor = Color.Black),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                    )
                },
            )
            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false },
                modifier = Modifier.wrapContentSize()
            ) {
                TrashType.values().forEach { value ->
                    DropdownMenuItem(onClick = {
                        trashType.value = value
                        expanded.value = false
                    }) {
                        Text(text = stringResource(id = value.toResourceId()))
                    }
                }
            }
        }
    }

    @Composable
    fun GradientButton(
        modifier: Modifier,
        text: String,
        onClick: () -> Unit
    ) {
        val gradient = Brush.verticalGradient(colors = listOf(lightBlack, black))

        Button(
            modifier = modifier,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            contentPadding = PaddingValues(),
            onClick = onClick,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(gradient),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = text, color = Color.Gray)
            }
        }
    }
}
