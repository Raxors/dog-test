package com.raxors.dog_test.data.backend

import com.google.gson.GsonBuilder
import com.raxors.dog_test.data.dto.DogDTO
import com.raxors.dog_test.data.dto.DogImageDTO
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BackendImpl @Inject constructor() : BackendApi {

    private lateinit var caller: BackendCaller

    init {
        buildCaller()
    }

    override fun getAllDogs(): Single<DogDTO> {
        return caller.getDogList()
    }

    override fun getAllBreedImages(breedName: String): Single<DogImageDTO> {
        return caller.getAllBreedImages(breedName)
    }

    private fun buildCaller() {
        val httpLoggingInterceptor =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        val okHttpClient = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS).build()
        val callAdapterFactory = RxJava2CallAdapterFactory.create()
        val converterFactory = GsonConverterFactory.create(GsonBuilder().create())
        caller = Retrofit.Builder()
            .baseUrl("https://dog.ceo")
            .client(okHttpClient)
            .addCallAdapterFactory(callAdapterFactory)
            .addConverterFactory(converterFactory)
            .build()
            .create(BackendCaller::class.java)
    }
}