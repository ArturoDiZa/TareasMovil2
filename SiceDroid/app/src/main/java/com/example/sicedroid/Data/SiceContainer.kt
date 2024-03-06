package com.example.sicedroid.Data

import android.app.Application

class   SiceContainer: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}