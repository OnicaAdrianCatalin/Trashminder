package com.example.trashminder.model

data class Reminder(
    val id: Int,
    val type: String,
    val date: String,
    val repetition: String
)
