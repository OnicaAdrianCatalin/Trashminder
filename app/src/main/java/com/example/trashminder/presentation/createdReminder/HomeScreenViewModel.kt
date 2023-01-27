package com.example.trashminder.presentation.createdReminder

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trashminder.model.ListOfReminders
import com.example.trashminder.repository.ReminderRepository
import com.example.trashminder.utils.Response
import kotlinx.coroutines.launch

class HomeScreenViewModel: ViewModel() {
    private val repository: ReminderRepository = ReminderRepository()
    var reminderResponse = mutableStateOf<Response<ListOfReminders>>(Response.Loading)

    init {
        getUserData()
    }

    private fun getUserData() {
        viewModelScope.launch {
            repository.getUserData().collect { response ->
                    reminderResponse.value = response
                }
            }
        }
    }


