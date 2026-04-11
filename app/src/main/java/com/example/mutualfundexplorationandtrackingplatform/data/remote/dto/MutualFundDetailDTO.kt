package com.example.mutualfundexplorationandtrackingplatform.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MutualFundDetailDTO(
    @SerialName("meta") val meta: MetaDto,
    @SerialName("data") val data: List<NavDataDto>,
    @SerialName("status") val status: String
)

@Serializable
data class MetaDto(
    @SerialName("fund_house") val fundHouse: String,
    @SerialName("scheme_type") val schemeType: String,
    @SerialName("scheme_category") val schemeCategory: String,
    @SerialName("scheme_code") val schemeCode: Int,
    @SerialName("scheme_name") val schemeName: String
)

@Serializable
data class NavDataDto(
    @SerialName("date") val date: String,
    @SerialName("nav") val nav: String
)