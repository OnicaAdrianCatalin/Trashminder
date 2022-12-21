package com.example.trashminder.repository

import com.example.trashminder.model.Reminder

class ReminderRepository {
    fun getAllData(): List<Reminder>{
        return listOf(
           Reminder(
               id=0,
               type="plastic",
               date="",
               repetition = "La 2 saptamani"
           ) ,
            Reminder(
                id=1,
                type="metal",
                date="",
                repetition = "La 2 saptamani"
            ) ,
            Reminder(
                id=2,
                type="hartie",
                date="",
                repetition = "saptamanal"
            ) ,
            Reminder(
                id=3,
                type="sticla",
                date="",
                repetition = "La 2 saptamani"
            ) ,
            Reminder(
                id=4,
                type="menajer",
                date="",
                repetition = "saptamanal"
            ) ,

        )
    }
}