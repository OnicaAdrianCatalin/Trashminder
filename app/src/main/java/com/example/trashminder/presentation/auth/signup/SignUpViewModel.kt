package com.example.trashminder.presentation.auth.signup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trashminder.presentation.auth.authUtils.AuthErrors
import com.example.trashminder.presentation.auth.authUtils.Result
import com.example.trashminder.presentation.auth.authUtils.assertFieldsEmpty
import com.example.trashminder.presentation.auth.authUtils.onFailure
import com.example.trashminder.presentation.auth.authUtils.onSuccess
import com.example.trashminder.services.FirebaseAuthService
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.launch

class SignUpViewModel(private val authService: FirebaseAuthService) : ViewModel() {
    private val _authResult = mutableStateOf<Result<AuthErrors, Unit>>((Result.Loading))
    val authResult: State<Result<AuthErrors, Unit>> = _authResult

    fun signUpUserWithEmailAndPassword(
        email: String,
        password: String,
        confirmPassword: String,
        firstName: String,
        lastName: String,
    ) {
        viewModelScope.launch {
            if (!assertFieldsEmpty(email, password, confirmPassword, firstName, lastName)) {
                _authResult.value = Result.Failure(AuthErrors.EMPTY_FIELDS)
                return@launch
            }

            if (confirmPassword != password) {
                _authResult.value = Result.Failure(AuthErrors.PASSWORD_MISMATCH)
                return@launch
            }

            authService.register(email, password).onSuccess {
                _authResult.value = Result.Success(Unit)
            }.onFailure {
                handleError(it as FirebaseException)
            }
        }
    }

    private fun handleError(exception: FirebaseException) {
        when (exception) {
            is FirebaseAuthUserCollisionException -> _authResult.value =
                Result.Failure(AuthErrors.EMAIL_ALREADY_IN_USE)
            is FirebaseNetworkException -> _authResult.value =
                Result.Failure(AuthErrors.NO_INTERNET_CONNECTION)
            else -> _authResult.value =
                Result.Failure(AuthErrors.SERVER_NOT_RESPONDING)
        }
    }
}

