package com.example.mutualfundexplorationandtrackingplatform.data.remote.mapper

import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.MutualFundDetail
import com.example.mutualfundexplorationandtrackingplatform.data.remote.dto.MutualFundDTO
import com.example.mutualfundexplorationandtrackingplatform.data.remote.dto.MutualFundResponseDTO

fun MutualFundDTO.toEntity(): MutualFundDetail {
    return MutualFundDetail(
        schemeCode = schemeCode,
        schemeName = schemeName,
        isInGrowth = isInGrowth,
        isInDivReinvestment = isInDivReinvestment,
        fundHouse = null,
        schemeType = null,
        schemeCategory = null,
        latestNav = null,
        latestNavDate = null,
        detailsIsFetched = false
    )
}


fun MutualFundDetail.toDTO(): MutualFundDTO {
    return MutualFundDTO(
        schemeCode = schemeCode,
        schemeName = schemeName,
        isInGrowth = isInGrowth,
        isInDivReinvestment = isInDivReinvestment,
        fundHouse = fundHouse,
        schemeType = schemeType,
        schemeCategory = schemeCategory,
        latestNav = latestNav,
        latestNavDate = latestNavDate
    )
}

fun MutualFundDTO.toEntityWithCategory(category: String): MutualFundDetail {
    return MutualFundDetail(
        schemeCode = schemeCode,
        schemeName = schemeName,
        isInGrowth = isInGrowth,
        isInDivReinvestment = isInDivReinvestment,
        fundHouse = null,
        schemeType = null,
        schemeCategory = category,
        latestNav = null,
        latestNavDate = null,
        detailsIsFetched = false
    )
}

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