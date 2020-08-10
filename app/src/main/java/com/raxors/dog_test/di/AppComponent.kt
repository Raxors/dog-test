package com.raxors.dog_test.di

import com.raxors.dog_test.DogApplication
import com.raxors.dog_test.ui.activities.MainActivity
import com.raxors.dog_test.data.di.DataModule
import com.raxors.dog_test.domain.di.DomainModule
import com.raxors.dog_test.ui.fragments.ImageListFavouriteFragment
import com.raxors.dog_test.ui.fragments.ImageListFragment
import com.raxors.dog_test.ui.view_models.DogViewModel
import com.raxors.dog_test.ui.view_models.FavouriteViewModel
import com.raxors.dog_test.ui.view_models.DogImageViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DomainModule::class, DataModule::class])
interface AppComponent {
    fun inject(dogApplication: DogApplication)
    fun inject(mainActivity: MainActivity)
    fun inject(dogViewModel: DogViewModel)
    fun inject(dogImageViewModel: DogImageViewModel)
    fun inject(favouriteViewModel: FavouriteViewModel)
    fun inject(fragment: ImageListFavouriteFragment)
    fun inject(fragment: ImageListFragment)
}