package com.raxors.dog_test.ui.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raxors.dog_test.DogApplication
import com.raxors.dog_test.data.entity.Dog
import com.raxors.dog_test.domain.use_cases.DogUseCases
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DogViewModel : ViewModel() {

    private val disposables = CompositeDisposable()

    @Inject
    lateinit var dogUseCases: DogUseCases

    val dogs: MutableLiveData<List<Dog>> = MutableLiveData()

    val error: MutableLiveData<String> = MutableLiveData()

    init {
        DogApplication.component.inject(this)
        disposables.addAll(
            dogUseCases.getAllDogs().subscribeOn(Schedulers.io()).subscribe { dogList, err ->
                if (err == null)
                    dogs.postValue(dogList)
                else {
                    error.postValue(err.message)
                }
            }
        )
    }
}