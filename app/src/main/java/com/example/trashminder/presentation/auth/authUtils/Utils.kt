package com.example.trashminder.presentation.auth.authUtils

 fun assertFieldsEmpty(vararg fields: String): Boolean {
    fields.find { it.isEmpty() || it.isBlank()}?.let { return false } ?: return true
}