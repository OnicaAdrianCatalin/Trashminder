package com.example.trashminder.presentation.auth.authUtils

import android.content.Context
import com.example.trashminder.R

enum class AuthErrors {
    PASSWORD_MISMATCH,
    EMAIL_ALREADY_IN_USE,
    EMPTY_FIELDS,
    EMAIL_OR_PASSWORD_INCORRECT,
    NO_INTERNET_CONNECTION,
    SERVER_NOT_RESPONDING
}

 fun AuthErrors.getAuthError(context: Context): String {
     return when (this) {
         AuthErrors.PASSWORD_MISMATCH -> context.getString(R.string.passwords_missmatch_validation)
         AuthErrors.EMPTY_FIELDS -> context.getString(R.string.fields_cannot_be_not_empty_validation)
         AuthErrors.EMAIL_ALREADY_IN_USE -> context.getString(R.string.email_already_exists_validation)
         AuthErrors.EMAIL_OR_PASSWORD_INCORRECT -> context.getString(R.string.email_or_password_incorrect_validation)
         AuthErrors.NO_INTERNET_CONNECTION -> context.getString(R.string.no_internet_connection_validation)
         AuthErrors.SERVER_NOT_RESPONDING -> context.getString(R.string.server_not_responding_validation)
     }
 }
