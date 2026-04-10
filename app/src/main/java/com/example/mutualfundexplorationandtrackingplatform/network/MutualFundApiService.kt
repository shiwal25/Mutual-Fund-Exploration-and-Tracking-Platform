package com.example.mutualfundexplorationandtrackingplatform.network

import retrofit2.http.GET

interface MutualFundApiService {

    @GET
    suspend fun getMutualFunds(): String
}