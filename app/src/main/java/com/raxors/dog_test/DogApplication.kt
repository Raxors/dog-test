package com.raxors.dog_test

import android.app.Application
import com.raxors.dog_test.data.di.BackendModule
import com.raxors.dog_test.data.di.DataModule
import com.raxors.dog_test.data.di.PreferencesModule
import com.raxors.dog_test.di.AppComponent
import com.raxors.dog_test.di.DaggerAppComponent

class DogApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupDI()
    }

    fun setupDI() {
        component = DaggerAppComponent.builder()
            .backendModule(BackendModule())
            .preferencesModule(PreferencesModule(applicationContext))
            .dataModule(DataModule())
            .build()
        component.inject(this)
    }

    companion object {
        lateinit var component: AppComponent
    }
}