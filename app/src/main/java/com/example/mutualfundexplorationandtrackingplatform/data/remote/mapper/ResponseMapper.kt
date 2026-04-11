package com.example.mutualfundexplorationandtrackingplatform.data.remote.mapper

import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.MutualFundDetail
import com.example.mutualfundexplorationandtrackingplatform.data.remote.dto.MutualFundResponseDTO

fun MutualFundResponseDTO.toEntity2(): MutualFundDetail {
    val latestNavData = data.firstOrNull()

    return MutualFundDetail(
        schemeCode = meta.schemeCode,
        schemeName = meta.schemeName,
        fundHouse = meta.fundHouse,
        schemeType = meta.schemeType,
        schemeCategory = meta.schemeCategory,
        latestNav = latestNavData?.nav,
        latestNavDate = latestNavData?.date,
        isInGrowth = meta.isinGrowth,
        isInDivReinvestment = meta.isinDivReinvestment,
        detailsIsFetched = true
    )
}