package com.example.mutualfundexplorationandtrackingplatform.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MutualFundDTO(
    @SerialName("schemeCode") val schemeCode: Int,
    @SerialName("schemeName") val schemeName: String,
    @SerialName("isinGrowth") val isinGrowth: String? = null,
    @SerialName("isinDivReinvestment") val isinDivReinvestment: String? = null
)
