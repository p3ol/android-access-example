package com.example.composeexample

import android.app.Application
import com.example.composeexample.data.AppContainer
import com.example.composeexample.data.AppContainerImpl

class ComposeExampleApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl(this)
    }
}
