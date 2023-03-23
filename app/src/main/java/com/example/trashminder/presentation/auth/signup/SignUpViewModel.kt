package com.example.trashminder.presentation.auth.signup

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
            if (assertFieldsNotEmpty(email, password, confirmPassword, firstName, lastName)) {
                if (confirmPassword == password) {
                    authService.register(email, password).onSuccess {
                        _authResult.value = Result.Success(Unit)
                    }.onFailure {
                        if (it is FirebaseAuthUserCollisionException) {
                            _authResult.value =
                                Result.Failure(AuthErrors.EMAIL_ALREADY_IN_USE)
                        }
                    }
                } else {
                    _authResult.value = Result.Failure(AuthErrors.PASSWORD_MISMATCH)
                }
            }else{
                _authResult.value = Result.Failure(AuthErrors.EMPTY_FIELDS)
            }
        }
    }
}
