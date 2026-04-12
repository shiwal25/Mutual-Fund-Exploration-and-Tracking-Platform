package com.example.mutualfundexplorationandtrackingplatform.data.remote.dto

data class NavDataResponse(
    val meta: MetaData,
    val data: List<NavPoint>,
    val status: String
)

data class MetaData(
    val fund_house: String,
    val scheme_type: String,
    val scheme_category: String,
    val scheme_code: Int,
    val scheme_name: String
)

data class NavPoint(
    val date: String,  // Format: "dd-MM-yyyy"
    val nav: String
)
