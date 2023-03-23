package com.example.trashminder.utils

import android.content.Context
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
 fun TimePeriod.toLocalizedString(context: Context): String{
    return when(this){
       TimePeriod.DAILY -> context.getString(R.string.time_period_daily)
       TimePeriod.WORKING_DAYS -> context.getString(R.string.time_period_working_days)
       TimePeriod.WEEKENDS -> context.getString(R.string.time_period_weekends)
       TimePeriod.WEEKLY -> context.getString(R.string.time_period_weekly)
       TimePeriod.EVERY_TWO_WEEKS -> context.getString(R.string.time_period_every_two_weeks)
       TimePeriod.MONTHLY -> context.getString(R.string.time_period_monthly)
       TimePeriod.EVERY_TWO_MONTHS -> context.getString(R.string.time_period_every_two_months)
    }
}
