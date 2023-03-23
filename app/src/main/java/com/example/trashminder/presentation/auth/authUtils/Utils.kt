package com.example.trashminder.presentation.auth.authUtils

 fun assertFieldsNotEmpty(vararg fields: String): Boolean {
    fields.find { it.isEmpty() }?.let { return false } ?: return true
}