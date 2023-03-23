package com.example.trashminder

import android.app.Application
import com.example.trashminder.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TrashminderApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@TrashminderApplication)
            modules(appModule)
        }
    }
}