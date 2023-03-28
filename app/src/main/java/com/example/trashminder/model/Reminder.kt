package com.example.trashminder.model

import com.example.trashminder.utils.TimePeriod
import com.example.trashminder.utils.TrashType

data class Reminder(
    val id: Int = 0,
    val type: TrashType = TrashType.Residual,
    val date: String = "",
    val repetition: TimePeriod = TimePeriod.WEEKLY
)
