package com.example.mutualfundexplorationandtrackingplatform.data.remote.mapper

import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.MutualFund
import com.example.mutualfundexplorationandtrackingplatform.data.remote.dto.MutualFundDetailDTO

fun MutualFundDetailDTO.toEntity(): MutualFund {
    return MutualFund(
        schemeCode = schemeCode,
        schemeName = schemeName,
        fundHouse= fundHouse,
        schemeType = schemeType,
        schemeCategory= schemeCategory,
        latestNav =latestNav,
        latestNavDate= latestNavDate,
        isInGrowth= isInGrowth,
        isInDivReinvestment =isInDivReinvestment,
    )
}

fun List<MutualFundDetailDTO>.toEntityList(): List<MutualFund> {
    return this.map { it.toEntity() }
}