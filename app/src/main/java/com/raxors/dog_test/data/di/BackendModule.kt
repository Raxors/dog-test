package com.raxors.dog_test.data.di

import com.raxors.dog_test.data.backend.BackendApi
import com.raxors.dog_test.data.backend.BackendImpl
import dagger.Module
import dagger.Provides

@Module
class BackendModule {

    @Provides
    fun provideBackendApi(backendImpl: BackendImpl): BackendApi = backendImpl

}