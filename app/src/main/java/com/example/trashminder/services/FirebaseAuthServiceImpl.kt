package com.example.trashminder.services

import com.example.trashminder.presentation.auth.authUtils.Result
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.tasks.await

class FirebaseAuthServiceImpl : FirebaseAuthService {
    override suspend fun login(email: String, password: String): Result<Exception, Unit> {
        return try {
            val task = FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            val result = task.await()
            handleResult (task, result)
        } catch (e: FirebaseAuthException) {
            Result.Failure(e)
        }
    }

    override suspend fun register(
        email: String,
        password: String
    ): Result<Exception, Unit> {
        return try {
            val task = FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            val result = task.await()
            handleResult(task, result)
        } catch (e: FirebaseAuthException) {
            Result.Failure(e)
        }
    }

    private fun handleResult(
        task: Task<AuthResult>,
        result: AuthResult
    ): Result<Exception, Unit> {
        return if (task.isSuccessful) {
            handleTaskSuccessful(result)
        } else {
            handleTaskFailed(task)
        }
    }

    private fun handleTaskSuccessful(result: AuthResult): Result<Exception, Unit> {
        return result.user?.let { Result.Success(Unit) } ?: Result.Failure(Exception("User is null"))
    }

    private fun handleTaskFailed(task: Task<AuthResult>): Result<Exception, Unit> {
        return task.exception?.let { Result.Failure(it) }
            ?: Result.Failure(Exception("Failed with no exception"))
    }
}