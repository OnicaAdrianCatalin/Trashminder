package com.example.trashminder.utils

import com.example.trashminder.R

enum class TimePeriod {
    WEEKLY,
    EVERY_TWO_WEEKS,
    EVERY_THREE_WEEKS,
    MONTHLY,
}

fun TimePeriod.toResourceId(): Int {
    return when (this) {
        TimePeriod.WEEKLY -> R.string.time_period_weekly
        TimePeriod.EVERY_TWO_WEEKS -> R.string.time_period_every_two_weeks
        TimePeriod.EVERY_THREE_WEEKS -> R.string.time_period_every_three_weeks
        TimePeriod.MONTHLY -> R.string.time_period_monthly
    }
}
