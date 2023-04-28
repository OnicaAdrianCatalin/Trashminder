package com.example.trashminder.presentation.auth.authUtils

 fun assertFieldsEmpty(vararg fields: String): Boolean {
    fields.find { it.isEmpty() || it.startsWith(" ")}?.let { return false } ?: return true
}