package com.example.trashminder.presentation.newReminder

import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.trashminder.presentation.theme.black
import com.example.trashminder.presentation.theme.bleu
import com.example.trashminder.presentation.theme.darkerGreen
import com.example.trashminder.presentation.theme.lightBlack
import com.example.trashminder.presentation.theme.lightGreen
import com.example.trashminder.utils.Constants
import java.util.Calendar
import java.util.Date

@Preview
@Composable
fun NewReminderScreen() {
    val expanded = remember {
        mutableStateOf(false)
    }
    val dateAndTimeDialogTrigger = remember {
        mutableStateOf(false)
    }
    val chosenDate = remember {
        mutableStateOf("Data si ora")
    }
    val timespan = remember { mutableStateOf<Int?>(null) }
    val trashType = remember { mutableStateOf("") }
    Scaffold(
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
                        text = "Salveaza"
                    ) {
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
                    text = "Alege tipul de gunoi",
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
                    Text(text = "Salveaza")
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
                text = "Repetare",
                modifier = Modifier.padding(8.dp),
                fontWeight = FontWeight.Bold
            )
            LazyColumn(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(start = 30.dp)
                    .fillMaxWidth()
            ) {
                items(Constants.timePeriod.size) { item ->
                    Row(modifier = Modifier.padding(12.dp)) {
                        RadioButton(
                            selected = timespan.value == item,
                            modifier = Modifier.size(20.dp),
                            onClick = {
                                timespan.value = item
                            }
                        )
                        Text(
                            text = Constants.timePeriod[item],
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
    calendar.time = Date()

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            chosenDate.value = "$mDayOfMonth/${mMonth + 1}/$mYear"
        }, year, month, day
    )
    val timePickerDialog = TimePickerDialog(
        context,
        { _, mHour: Int, mMinute: Int ->
            chosenDate.value += " $mHour:$mMinute"
        }, hour, minutes, false
    )
    datePickerDialog.show()
    datePickerDialog.setOnDismissListener {
        timePickerDialog.show()
    }
}

@Composable
private fun DropDownButton(
    trashType: MutableState<String>,
    expanded: MutableState<Boolean>
) {
    Column {
        OutlinedTextField(
            value = trashType.value, onValueChange = { trashType.value = it },
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
            Constants.trashTypeElements.forEach { value ->
                DropdownMenuItem(onClick = {
                    trashType.value = value
                    expanded.value = false
                }) {
                    Text(text = value)
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
