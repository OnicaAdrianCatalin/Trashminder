package com.example.trashminder.utils

import com.example.trashminder.R

enum class TimePeriod {
    DAILY,
    WORKING_DAYS,
    WEEKENDS,
    WEEKLY,
    EVERY_TWO_WEEKS,
    MONTHLY,
    EVERY_TWO_MONTHS
}
 fun TimePeriod.toResourceId(): Int{
    return when(this){
       TimePeriod.DAILY -> R.string.time_period_daily
       TimePeriod.WORKING_DAYS -> R.string.time_period_working_days
       TimePeriod.WEEKENDS -> R.string.time_period_weekends
       TimePeriod.WEEKLY -> R.string.time_period_weekly
       TimePeriod.EVERY_TWO_WEEKS -> R.string.time_period_every_two_weeks
       TimePeriod.MONTHLY -> R.string.time_period_monthly
       TimePeriod.EVERY_TWO_MONTHS -> R.string.time_period_every_two_months
    }
}
