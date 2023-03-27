package com.example.trashminder.di

import com.example.trashminder.presentation.auth.login.LoginViewModel
import com.example.trashminder.presentation.auth.signup.SignUpViewModel
import com.example.trashminder.services.FirebaseAuthService
import com.example.trashminder.services.FirebaseAuthServiceImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<FirebaseAuthService> { FirebaseAuthServiceImpl() }
    viewModel {
        LoginViewModel(FirebaseAuthServiceImpl())
    }
    viewModel {
        SignUpViewModel(FirebaseAuthServiceImpl())
    }
}
