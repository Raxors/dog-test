package com.raxors.dog_test.ui.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raxors.dog_test.DogApplication
import com.raxors.dog_test.domain.use_cases.DogUseCases
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DogImageViewModel(breedName: String) : ViewModel() {

    private val disposables = CompositeDisposable()

    @Inject
    lateinit var dogUseCases: DogUseCases

    val images: MutableLiveData<List<String>> = MutableLiveData()

    val error: MutableLiveData<String> = MutableLiveData()
    init {
        DogApplication.component.inject(this)
        disposables.addAll(
            dogUseCases.getAllBreedImages(breedName).subscribeOn(Schedulers.io()).subscribe { imageList, err ->
                if (err == null)
                    images.postValue(imageList)
                else {
                    error.postValue(err.message)
                }
            }
        )
    }
}