package com.example.mutualfundexplorationandtrackingplatform.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MutualFundDetailDTO(
    @SerialName("schemeCode")
    val schemeCode: Int?,

    @SerialName("schemeName")
    val schemeName: String?,

    @SerialName("fundHouse")
    val fundHouse: String?,

    @SerialName("schemeType")
    val schemeType: String?,

    @SerialName("schemeCategory")
    val schemeCategory: String?,

    @SerialName("nav")
    val latestNav: String?,

    @SerialName("date")
    val latestNavDate: String?,

    @SerialName("isinGrowth")
    val isInGrowth: String?,

    @SerialName("isinDivReinvestment")
    val isInDivReinvestment: String?

)