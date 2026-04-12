package com.example.mutualfundexplorationandtrackingplatform.di

import android.content.Context
import com.example.mutualfundexplorationandtrackingplatform.data.local.MutualFundDatabase
import com.example.mutualfundexplorationandtrackingplatform.data.local.dao.WatchListDao
import com.example.mutualfundexplorationandtrackingplatform.data.remote.api.MutualFundApiService
import com.example.mutualfundexplorationandtrackingplatform.data.repository.MutualFundRepository
import com.example.mutualfundexplorationandtrackingplatform.data.repository.MutualFundRepositoryImpl
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val mutualFundRepository: MutualFundRepository
    val watchListDao: WatchListDao
}

class AppDataContainer(private val context: Context) : AppContainer {
    private val baseUrl = "https://api.mfapi.in/"

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json; charset=UTF8".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService : MutualFundApiService by lazy {
        retrofit.create(MutualFundApiService::class.java)
    }

    private val database: MutualFundDatabase by lazy {
        MutualFundDatabase.getDatabase(context)
    }

    override val watchListDao: WatchListDao by lazy {
        database.watchListDao()
    }

    override val mutualFundRepository: MutualFundRepository by lazy {
        MutualFundRepositoryImpl(database.mutualFundDAO(), retrofitService)
    }
}