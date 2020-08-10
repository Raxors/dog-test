package com.raxors.dog_test.data.di

import android.content.Context
import com.raxors.dog_test.data.preferences.PreferencesApi
import com.raxors.dog_test.data.preferences.PreferencesImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PreferencesModule(private val context: Context) {

    @Provides
    @Singleton
    fun providePreferencesApi() : PreferencesApi {
        return PreferencesImpl(context)
    }
}