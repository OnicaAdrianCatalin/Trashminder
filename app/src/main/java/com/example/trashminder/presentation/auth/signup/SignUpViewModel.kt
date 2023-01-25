package com.example.trashminder.presentation.auth.signup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.trashminder.presentation.auth.authUtils.AuthErrors
import com.example.trashminder.presentation.auth.authUtils.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException

class SignUpViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val _authResult = mutableStateOf<Result<AuthErrors, Unit>>((Result.Loading))
    val authResult: State<Result<AuthErrors, Unit>> = _authResult

    fun signUpUserWithEmailAndPassword(
        email: String,
        password: String,
        confirmPassword: String,
        firstName: String,
        lastName: String,
    ) {
        if (assertFieldsNotEmpty(email, password, confirmPassword, firstName, lastName)) {
            _authResult.value = Result.Failure(AuthErrors.EMPTY_FIELDS)
        } else {
            if (confirmPassword == password) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            _authResult.value = Result.Success(Unit)
                        } else {
                            if (task.exception is FirebaseAuthUserCollisionException) {
                                _authResult.value =
                                    Result.Failure(AuthErrors.EMAIL_ALREADY_IN_USE)
                            }
                        }
                    }
            } else {
                _authResult.value = Result.Failure(AuthErrors.PASSWORD_MISMATCH)
            }
        }
    }

    private fun assertFieldsNotEmpty(
        email: String,
        password: String,
        confirmPassword: String,
        firstName: String,
        lastName: String,
    ): Boolean {
        return email.isEmpty() or
                password.isEmpty() or
                confirmPassword.isEmpty() or
                firstName.isEmpty() or
                lastName.isEmpty()
    }
}
