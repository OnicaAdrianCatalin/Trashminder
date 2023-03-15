package com.example.trashminder.presentation.auth.signup

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import com.example.trashminder.R
import com.example.trashminder.presentation.auth.authUtils.AuthErrors
import com.example.trashminder.presentation.auth.authUtils.onFailure
import com.example.trashminder.presentation.auth.authUtils.onSuccess
import com.example.trashminder.presentation.theme.black
import com.example.trashminder.presentation.theme.lightBlack
import com.example.trashminder.presentation.theme.lightBlue
import com.example.trashminder.presentation.theme.lightGreen
import com.example.trashminder.utils.CustomSnackBar
import com.example.trashminder.utils.CustomTextField
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignUpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                SignUpScreen()
            }
        }
    }

    @Composable
    fun SignUpScreen() {
        val viewModel = viewModel<SignUpViewModel>()
        val firstName = remember {
            mutableStateOf("")
        }
        val lastName = remember {
            mutableStateOf("")
        }
        val email = remember {
            mutableStateOf("")
        }
        val password = remember {
            mutableStateOf("")
        }
        val passwordConfirmation = remember {
            mutableStateOf("")
        }
        val errorText = remember {
            mutableStateOf("")
        }
        val snackState = remember { SnackbarHostState() }
        val coroutineScope = rememberCoroutineScope()
        ObserveSignUpViewModel(
            viewModel = viewModel,
            authError = errorText
        )
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
                    text = "Creeaza un cont",
                    modifier = Modifier.padding(8.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                CustomTextField(
                    label = "Nume",
                    textFieldValue = firstName,
                    visualTransformation = VisualTransformation.None
                )
                CustomTextField(
                    label = "Prenume",
                    textFieldValue = lastName,
                    visualTransformation = VisualTransformation.None
                )
                CustomTextField(
                    label = "Email",
                    textFieldValue = email,
                    visualTransformation = VisualTransformation.None
                )
                CustomTextField(
                    label = "Parola",
                    textFieldValue = password,
                    visualTransformation = PasswordVisualTransformation()
                )
                CustomTextField(
                    label = "Confirmare parola",
                    textFieldValue = passwordConfirmation,
                    visualTransformation = PasswordVisualTransformation()
                )

                Button(
                    onClick = {
                        viewModel.signUpUserWithEmailAndPassword(
                            email.value,
                            password.value,
                            passwordConfirmation.value,
                            firstName.value,
                            lastName.value
                        )
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
                    Text(text = "Inregistrare", color = Color.White)
                }

                Text(
                    text = "Ai deja un cont? Autentificare",
                    modifier = Modifier
                        .padding(bottom = 50.dp)
                        .clickable {
                            findNavController().popBackStack()
                        }
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    SnackbarHost(
                        hostState = snackState,
                    ) {
                        CustomSnackBar(text = errorText)
                    }
                }
            }
        }
    }

    @Composable
    fun ObserveSignUpViewModel(
        viewModel: SignUpViewModel,
        authError: MutableState<String>
    ) {
        viewModel.authResult.value.onSuccess {
            findNavController().navigate(R.id.action_signUpFragment_to_newReminderFragment)
        }.onFailure { error: AuthErrors ->
            when (error) {
                AuthErrors.PASSWORD_MISMATCH -> {
                    authError.value = "Passwords are not matching"
                }
                AuthErrors.EMPTY_FIELDS -> {
                    authError.value = "Fields cannot be empty"
                }
                AuthErrors.EMAIL_ALREADY_IN_USE -> {
                    authError.value = "The Email already exists"
                }
                else -> {}
            }
        }
    }
}
