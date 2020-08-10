package com.raxors.dog_test.ui.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raxors.dog_test.DogApplication
import com.raxors.dog_test.data.entity.FavouriteMap
import com.raxors.dog_test.domain.use_cases.FavouriteUseCases
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FavouriteViewModel : ViewModel() {

    private val disposables = CompositeDisposable()

    @Inject
    lateinit var favouriteUseCases: FavouriteUseCases

    val favourites: MutableLiveData<FavouriteMap> = MutableLiveData()
    init {
        DogApplication.component.inject(this)
        disposables.addAll(favouriteUseCases.favouriteObservable
            .subscribeOn(Schedulers.io())
            .subscribe {favouriteMap ->
                favourites.postValue(favouriteMap)
            })
    }
}