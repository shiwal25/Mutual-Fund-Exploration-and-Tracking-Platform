package com.example.mutualfundexplorationandtrackingplatform.network

import com.example.mutualfundexplorationandtrackingplatform.data.MutualFund
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface MutualFundApiService {

    @GET()
    suspend fun getMutualFunds(): List<MutualFund>
}