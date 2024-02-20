package com.example.sice

import android.app.Application
import com.example.sice.data.AppContainer
import com.example.sice.data.DefaultAppContainer


class AlumnosApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}