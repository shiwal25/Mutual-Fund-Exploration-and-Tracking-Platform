package com.example.mutualfundexplorationandtrackingplatform.data.remote.mapper

import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.MutualFundDetail
import com.example.mutualfundexplorationandtrackingplatform.data.remote.dto.MutualFundDTO

fun MutualFundDTO.toEntity(): MutualFundDetail {
    return MutualFundDetail(
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

fun List<MutualFundDTO>.toEntityList(): List<MutualFundDetail> {
    return this.map { it.toEntity() }
}