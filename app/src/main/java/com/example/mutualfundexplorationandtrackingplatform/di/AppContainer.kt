package com.example.mutualfundexplorationandtrackingplatform.di

import android.content.Context
import com.example.mutualfundexplorationandtrackingplatform.data.repository.MutualFundRepositoryImpl
import com.example.mutualfundexplorationandtrackingplatform.data.local.MutualFundDatabase
import com.example.mutualfundexplorationandtrackingplatform.data.local.dao.WatchListDao
import com.example.mutualfundexplorationandtrackingplatform.data.repository.MutualFundRepository
import com.example.mutualfundexplorationandtrackingplatform.data.remote.api.MutualFundApiService
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val mutualFundRepository: MutualFundRepository
    val watchListDao: WatchListDao

}

class AppDataContainer(private val context: Context) : AppContainer {
    private val baseUrl = "https://api.mfapi.in/"

    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()

    private val retrofitService : MutualFundApiService by lazy {
        retrofit.create(MutualFundApiService::class.java)
    }

    private val database: MutualFundDatabase by lazy {
        MutualFundDatabase.getDatabase(context)
    }

    // 3. Expose the WatchListDao
    override val watchListDao: WatchListDao by lazy {
        database.watchListDao() // Note: Ensure this matches the exact function name in your MutualFundDatabase class!
    }

    override val mutualFundRepository: MutualFundRepository by lazy {
        MutualFundRepositoryImpl(database.mutualFundDAO(), retrofitService)
    }
}