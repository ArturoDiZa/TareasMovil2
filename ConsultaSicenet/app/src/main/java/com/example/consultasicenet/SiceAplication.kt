package com.example.consultasicenet

import android.app.Application
import com.example.consultasicenet.data.AppContainer
import com.example.consultasicenet.data.DefaultAppContainer

class SiceAplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}