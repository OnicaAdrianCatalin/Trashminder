package com.example.trashminder.services

import com.example.trashminder.presentation.auth.authUtils.Result
import com.google.firebase.auth.FirebaseUser

interface FirebaseAuthService {
        suspend fun login(email: String, password: String): Result<Exception, Unit>
        suspend fun register(email: String, password: String): Result<Exception, Unit>
}