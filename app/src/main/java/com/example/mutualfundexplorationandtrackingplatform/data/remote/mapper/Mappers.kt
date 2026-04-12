package com.example.mutualfundexplorationandtrackingplatform.data.remote.mapper

import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.MutualFundDetail
import com.example.mutualfundexplorationandtrackingplatform.data.remote.dto.MutualFundDTO

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

fun List<MutualFundDTO>.toEntityList(): List<MutualFundDetail> {
    return this.map { it.toEntity() }
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