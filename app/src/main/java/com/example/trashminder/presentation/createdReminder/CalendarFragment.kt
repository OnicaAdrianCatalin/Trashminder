package com.example.trashminder.presentation.createdReminder

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trashminder.R
import com.example.trashminder.presentation.theme.*
import java.util.*
import kotlin.properties.Delegates

class CalendarFragment : Fragment() {

    private val monthNames = arrayOf(
        "January", "February", "March", "April",
        "May", "June", "July", "August",
        "September", "October", "November", "December"
    )
    private val weekNames = arrayOf(
        R.string.monday,
        R.string.tuesday,
        R.string.wednesday,
        R.string.thursday,
        R.string.friday,
        R.string.saturday,
        R.string.sunday
    )

    private var calendar = Calendar.getInstance()

    private var numberOfDaysInPrevMonth by Delegates.notNull<Int>()
    private var daysLeftInFirstWeek by Delegates.notNull<Int>()
    private var indexOfDayAfterLastDayOfMonth by Delegates.notNull<Int>()

    private val currentDateMonth = calendar[Calendar.MONTH]
    private val currentDateYear = calendar[Calendar.YEAR]
    private val daysInCurrentMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

    private val days = arrayOfNulls<Int>(44)
    private var daysLeft = arrayOfNulls<Int>(44)
    private var dateList = emptyList<String>()
    private var yearAndTimeList = emptyList<String>()
    private var dates = arrayOfNulls<String>(44)
    private var types = arrayOfNulls<String>(44)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                CalendarScreen()
            }
        }
    }

    private fun createDaysOfCurrentMonth() {

        calendar.set(currentDateYear, currentDateMonth, 1)
        val firstDayOfCurrentMonth = calendar[Calendar.DAY_OF_WEEK]

        daysLeftInFirstWeek = 0
        indexOfDayAfterLastDayOfMonth = 0
        numberOfDaysInPrevMonth = 0

        var dayNumber = 1
        if (firstDayOfCurrentMonth != 1) {
            daysLeftInFirstWeek = firstDayOfCurrentMonth
            indexOfDayAfterLastDayOfMonth = daysLeftInFirstWeek + daysInCurrentMonth

            for (i in firstDayOfCurrentMonth until firstDayOfCurrentMonth + daysInCurrentMonth) {
                days[i - 1] = dayNumber
                dayNumber++
            }
        } else {
            daysLeftInFirstWeek = 8
            indexOfDayAfterLastDayOfMonth = daysLeftInFirstWeek + daysInCurrentMonth
            for (i in daysLeftInFirstWeek until daysLeftInFirstWeek + daysInCurrentMonth) {
                days[i - 1] = dayNumber
                dayNumber++
            }
        }
    }

    private fun createDaysOfPrevMonth() {
        if (currentDateMonth > 0) {
            calendar.set(currentDateYear, currentDateMonth - 1, 1)
        } else {
            calendar.set(currentDateYear - 1, 11, 1)
        }

        var daysInPreviousMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        Log.d("Main", "daysInPreviousMonth = $daysInPreviousMonth")

        for (i in daysLeftInFirstWeek - 1 downTo 0) {
            if (i == 0) break

            days[i - 1] = daysInPreviousMonth
            daysLeft[i - 1] = daysInPreviousMonth

            numberOfDaysInPrevMonth++
            daysInPreviousMonth--
        }
    }

    private fun createDaysOfNextMonth() {
        var nextMonthDaysCount = 1
        for (i in indexOfDayAfterLastDayOfMonth until days.size) {
            days[i - 1] = nextMonthDaysCount
            daysLeft[i - 1] = nextMonthDaysCount
            nextMonthDaysCount++
        }
    }

    private fun getReminders(viewModel: CalendarViewModel) {
        viewModel.reminderResponse.value?.reminders?.forEach {
            val date = it.date
            val type = it.type.name

            val delim1 = "/"
            val delim2 = " "

            dateList = date.split(delim1)
            yearAndTimeList = dateList[2].split(delim2)

            dates[dateList[0].toInt() + numberOfDaysInPrevMonth - 1] = dateList[0]
            types[dateList[0].toInt() + numberOfDaysInPrevMonth - 1] = type
        }
    }

    @Preview
    @Composable
    fun CalendarScreen() {
        val viewModel = viewModel<CalendarViewModel>()

        createDaysOfCurrentMonth()
        createDaysOfPrevMonth()
        createDaysOfNextMonth()
        getReminders(viewModel)

        Scaffold(
            modifier = Modifier
                .background(Color.White).padding(5.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(text = monthNames[currentDateMonth], fontSize = 30.sp)
                Spacer(modifier = Modifier.size(30.dp))
                DaysOfWeek()

                var min = 1
                var max = 1
                for (weekNumber in 1..6) {

                    Row() {
                        when (weekNumber) {
                            1 -> {
                                min = 1;max = 7
                            }
                            2 -> {
                                min = 8;max = 14
                            }
                            3 -> {
                                min = 15;max = 21
                            }
                            4 -> {
                                min = 22;max = 28
                            }
                            5 -> {
                                min = 29;max = 35
                            }
                            6 -> {
                                min = 36;max = 42
                            }
                        }
                        DaysOfMonth(min, max)
                    }
                }
            }
        }
    }

    @Composable
    fun DaysOfWeek() {
        Row() {
            for (i in weekNames) {
                TextButton(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f)) {
                    Text(text = stringResource(id = i), color = darkerGreen)
                }
            }
        }
    }

    @Composable
    fun DaysOfMonth(min: Int, max: Int) {
        Row() {
            for (i in min..max) {
                if (days[i] == daysLeft[i]) {
                    TextButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.weight(1f)
                    ) {
                        val text = days[i].toString()
                        Text(text = text, color = Color.LightGray)
                    }
                } else if (dates[i] == days[i].toString() && currentDateMonth + 1 == dateList[1].toInt() && currentDateYear == yearAndTimeList[0].toInt()) {

                    var color = bleu
                    when (types[i]) {
                        "Plastic" -> color = yellow
                        "Residual" -> color = black
                        "Glass" -> color = greenTrash
                        "Paper" -> color = blueTrash
                    }

                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(backgroundColor = color)
                    ) {
                        val text = days[i].toString()
                        Text(text = text, color = Color.White)
                    }
                } else {
                    TextButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.weight(1f)
                    ) {
                        val text = days[i].toString()
                        Text(text = text, color = Color.Black)
                    }
                }
            }
        }
    }
}