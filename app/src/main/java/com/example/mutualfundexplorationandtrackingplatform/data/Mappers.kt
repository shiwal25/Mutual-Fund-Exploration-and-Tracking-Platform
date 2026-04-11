package com.example.mutualfundexplorationandtrackingplatform.data

import com.example.mutualfundexplorationandtrackingplatform.network.MutualFundDTO

fun MutualFundDTO.toEntity() = MutualFund(
    schemeCode = schemeCode,
    schemeName = schemeName,
    isinGrowth = isinGrowth,
    isinDivReinvestment = isinDivReinvestment
)