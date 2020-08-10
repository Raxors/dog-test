package com.raxors.dog_test.data.preferences

import io.reactivex.subjects.BehaviorSubject

interface PreferencesApi {
    var favourites: String
    val favouriteObservable: BehaviorSubject<String>
}