package com.example.mutualfundexplorationandtrackingplatform.data

import android.content.Context
import com.example.mutualfundexplorationandtrackingplatform.network.MutualFundApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val mutualFundRepository: MutualFundRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    private val baseUrl = "https://www.mfapi.in/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService : MutualFundApiService by lazy {
        retrofit.create(MutualFundApiService::class.java)
    }

    override val mutualFundRepository: MutualFundRepository by lazy {
        MFRepository(MutualFundDatabase.getDatabase(context), retrofitService)
    }
}