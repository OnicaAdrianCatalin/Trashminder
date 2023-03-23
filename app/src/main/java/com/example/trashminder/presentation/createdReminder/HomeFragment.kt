package com.example.trashminder.presentation.createdReminder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import com.example.trashminder.R

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                HomeScreen()
            }
        }
    }

    @Composable
    fun HomeScreen() {
        val viewModel = viewModel<HomeScreenViewModel>()
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            val state = rememberLazyListState()
            LazyColumn(horizontalAlignment = Alignment.CenterHorizontally, state = state) {
                items(viewModel.reminderResponse.value?.reminders ?: emptyList()) {
                    CustomItem(reminder = it, requireContext())
                }
                item {
                    IconButton(onClick = {
                        findNavController().navigate(R.id.newReminderFragment)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add_circle),
                            contentDescription = getString(R.string.add_button_content_description),
                            modifier = Modifier.size(90.dp),
                            tint = Color.LightGray
                        )
                    }
                }
            }
        }
    }
}
