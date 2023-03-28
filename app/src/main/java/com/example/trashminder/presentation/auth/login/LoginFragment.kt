package com.example.trashminder.presentation.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.trashminder.R
import com.example.trashminder.presentation.auth.authUtils.AuthErrors
import com.example.trashminder.presentation.auth.authUtils.getAuthError
import com.example.trashminder.presentation.auth.authUtils.onFailure
import com.example.trashminder.presentation.auth.authUtils.onSuccess
import com.example.trashminder.presentation.theme.black
import com.example.trashminder.presentation.theme.lightBlack
import com.example.trashminder.presentation.theme.lightBlue
import com.example.trashminder.presentation.theme.lightGreen
import com.example.trashminder.utils.CustomSnackBar
import com.example.trashminder.utils.CustomTextField
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get

class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                LoginScreen()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        checkIfUserSignedIn()
    }

    private fun checkIfUserSignedIn() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user?.uid != null) {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }

    @Composable
    fun LoginScreen() {
        val email = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }
        val viewModel = get<LoginViewModel>()
        val errorText = remember { mutableStateOf("") }
        val snackState = remember { SnackbarHostState() }
        val coroutineScope = rememberCoroutineScope()

        ObserveViewModel(viewModel = viewModel, errorText = errorText)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(lightGreen, lightBlue)
                    )
                )
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = getString(R.string.enter_account_text),
                    modifier = Modifier.padding(12.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                CustomTextField(
                    label = getString(R.string.email_label),
                    textFieldValue = email,
                )
                CustomTextField(
                    label = getString(R.string.password_label),
                    textFieldValue = password,
                    visualTransformation = PasswordVisualTransformation()
                )

                Button(
                    onClick = {
                        viewModel.signInWithEmailAndPassword(email.value, password.value)
                        coroutineScope.launch {
                            delay(100)
                            if (errorText.value.isNotEmpty()) {
                                snackState.showSnackbar(errorText.value)
                                errorText.value = ""
                            }
                        }
                    },

                    modifier = Modifier
                        .padding(12.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                listOf(lightBlack, black)
                            )
                        ),
                    elevation = ButtonDefaults.elevation(0.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                ) {
                    Text(text = getString(R.string.login_text), color = Color.White)
                }
                Text(
                    text = getString(R.string.no_account_text),
                    modifier = Modifier.clickable {
                        findNavController().navigate(R.id.signUpFragment)
                    }
                )
                SnackbarHost(hostState = snackState) {
                    CustomSnackBar(text = errorText)
                }
            }
        }
    }

    @Composable
    fun ObserveViewModel(
        viewModel: LoginViewModel,
        errorText: MutableState<String>
    ) {
        viewModel.authResult.value.onSuccess {
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
            .onFailure { error: AuthErrors ->
              errorText.value = error.getAuthError(requireContext())
            }
        }
    }
