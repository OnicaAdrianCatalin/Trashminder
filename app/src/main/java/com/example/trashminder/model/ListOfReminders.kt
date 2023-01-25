package com.example.trashminder.model

data class ListOfReminders(
    val reminders: MutableList<Reminder> = listOf<Reminder>().toMutableList()
)
