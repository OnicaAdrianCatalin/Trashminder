package com.example.trashminder.presentation.auth.login

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.trashminder.presentation.auth.authUtils.AuthErrors
import com.example.trashminder.presentation.auth.authUtils.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class LoginViewModel : ViewModel() {
    val auth = FirebaseAuth.getInstance()
    private val _authResult = mutableStateOf<Result<AuthErrors, Unit>>((Result.Loading))
    val authResult: State<Result<AuthErrors, Unit>> = _authResult

    fun signInWithEmailAndPassword(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signedIn successfully ")
                        _authResult.value = Result.Success(Unit)
                    } else {
                        if (task.exception is IllegalArgumentException) {
                            _authResult.value =
                                    Result.Failure(
                                        AuthErrors.EMPTY_FIELDS
                                    )
                        }
                        if (task.exception is FirebaseAuthInvalidUserException) {
                            _authResult.value =
                                    Result.Failure(
                                        AuthErrors.EMAIL_OR_PASSWORD_INCORRECT
                                    )
                        }
                        Log.e(TAG, "signIn failed", task.exception)
                    }
                }
        } else {
            _authResult.value = Result.Failure(AuthErrors.EMPTY_FIELDS)
        }
    }

    companion object {
        private const val TAG = "LoginViewModel"
    }
}
