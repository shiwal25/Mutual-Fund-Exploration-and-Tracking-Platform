package com.example.mutualfundexplorationandtrackingplatform.data.remote.api

import com.example.mutualfundexplorationandtrackingplatform.data.remote.dto.MutualFundDTO
import com.example.mutualfundexplorationandtrackingplatform.data.remote.dto.MutualFundResponseDTO
import com.example.mutualfundexplorationandtrackingplatform.data.remote.dto.NavDataResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MutualFundApiService {

    @GET("mf")
    suspend fun getMutualFunds(
        @Query("limit")  limit: Int  = 100,
        @Query("offset") offset: Int = 0
    ): List<MutualFundDTO>

    @GET("mf/{scheme_code}/latest")
    suspend fun getFundDetails(
        @Path("scheme_code") schemeCode: Int?
    ): MutualFundResponseDTO

    @GET("mf/search")
    suspend fun getFundsByCategory(
        @Query("q") q:String
    ): List<MutualFundDTO>

    @GET("mf/{schemeCode}")
    suspend fun getNavData(
        @Path("schemeCode") schemeCode: String,
        @Query("startDate") startDate: String? = null,
        @Query("endDate") endDate: String? = null
    ): NavDataResponse
}