package com.raxors.dog_test.data.preferences

import android.content.Context
import android.content.SharedPreferences
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesImpl @Inject constructor(context: Context) : PreferencesApi {

    private val APP_PREFERENCES = "SETTINGS"
    private val APP_PREFERENCES_FAVOURITES = "FAVOURITES"

    var mSettings: SharedPreferences

    init {
        mSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
    }

    override var favourites: String
        get() = mSettings.getString(APP_PREFERENCES_FAVOURITES, "")!!
        set(value) {
            mSettings.edit().putString(APP_PREFERENCES_FAVOURITES, value).apply()
            favouriteObservable.onNext(value)
        }

    override val favouriteObservable = BehaviorSubject.create<String>().apply { onNext(favourites) }
}