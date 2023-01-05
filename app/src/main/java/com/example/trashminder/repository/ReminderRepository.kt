package com.example.trashminder.repository

import com.example.trashminder.model.Reminder

class ReminderRepository {
    fun getAllData(): List<Reminder>{
        return listOf(
           Reminder(
               id=0,
               type="plastic",
               date="18/01/2023 16:19",
               repetition = "La 2 saptamani"
           ) ,
            Reminder(
                id=1,
                type="metal",
                date="18/01/2023 16:19",
                repetition = "La 2 saptamani"
            ) ,
            Reminder(
                id=2,
                type="hartie",
                date="18/01/2023 16:19",
                repetition = "saptamanal"
            ) ,
            Reminder(
                id=3,
                type="sticla",
                date="18/01/2023 16:19",
                repetition = "La 2 saptamani"
            ) ,
            Reminder(
                id=4,
                type="menajer",
                date="18/01/2023 16:19",
                repetition = "saptamanal"
            ) ,

        )
    }
}