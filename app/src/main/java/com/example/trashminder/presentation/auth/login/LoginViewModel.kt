package com.example.trashminder.presentation.auth.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trashminder.presentation.auth.authUtils.AuthErrors
import com.example.trashminder.presentation.auth.authUtils.Result
import com.example.trashminder.presentation.auth.authUtils.assertFieldsNotEmpty
import com.example.trashminder.presentation.auth.authUtils.onFailure
import com.example.trashminder.presentation.auth.authUtils.onSuccess
import com.example.trashminder.services.FirebaseAuthService
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlinx.coroutines.launch

class LoginViewModel(private val authService: FirebaseAuthService) : ViewModel() {
    val auth = FirebaseAuth.getInstance()
    private val _authResult = mutableStateOf<Result<AuthErrors, Unit>>((Result.Loading))
    val authResult: State<Result<AuthErrors, Unit>> = _authResult

    fun signInWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            if (!assertFieldsNotEmpty(email, password)) {
                _authResult.value = Result.Failure(AuthErrors.EMPTY_FIELDS)
                return@launch
            }
                authService.login(email, password).onSuccess {
                    _authResult.value = Result.Success(Unit)
                }.onFailure {
                    if (it is FirebaseAuthInvalidCredentialsException) {
                        _authResult.value =
                            Result.Failure(
                                AuthErrors.EMAIL_OR_PASSWORD_INCORRECT
                            )
                    }
                    if (it is FirebaseNetworkException) {
                        _authResult.value =
                            Result.Failure(
                                AuthErrors.NO_INTERNET_CONNECTION
                            )
                    }
                       if (it is FirebaseException) {
                        _authResult.value =
                            Result.Failure(
                                AuthErrors.SERVER_NOT_RESPONDING
                            )
                    }
                }
            }
        }
    }
