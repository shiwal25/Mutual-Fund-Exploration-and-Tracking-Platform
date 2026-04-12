package com.example.mutualfundexplorationandtrackingplatform.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NavDataResponse(
    val meta: MetaData,
    val data: List<NavPoint>,
    val status: String
)

@Serializable
data class MetaData(
    @SerialName("fund_house")       val fundHouse: String,
    @SerialName("scheme_type")      val schemeType: String,
    @SerialName("scheme_category")  val schemeCategory: String,
    @SerialName("scheme_code")      val schemeCode: Int,
    @SerialName("scheme_name")      val schemeName: String
)

@Serializable
data class NavPoint(
    val date: String,
    val nav: String
)
