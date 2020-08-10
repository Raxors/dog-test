package com.raxors.dog_test.data.di

import dagger.Module

@Module(includes = [RepositoryModule::class, BackendModule::class, PreferencesModule::class])
class DataModule {
}