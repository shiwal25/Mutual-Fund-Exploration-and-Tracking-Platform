package com.example.mutualfundexplorationandtrackingplatform.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MutualFundResponseDTO(
    @SerialName("meta") val meta: MetaDTO,
    @SerialName("data") val data: List<NavDataDTO>,
    @SerialName("status") val status: String
)

@Serializable
data class MetaDTO(
    @SerialName("fund_house") val fundHouse: String?,
    @SerialName("scheme_type") val schemeType: String?,
    @SerialName("scheme_category") val schemeCategory: String?,
    @SerialName("scheme_code") val schemeCode: Int?,
    @SerialName("scheme_name") val schemeName: String?,
    @SerialName("isin_growth") val isinGrowth: String?,
    @SerialName("isin_div_reinvestment") val isinDivReinvestment: String?
)

@Serializable
data class NavDataDTO(
    @SerialName("date") val date: String?,
    @SerialName("nav") val nav: String?
)
