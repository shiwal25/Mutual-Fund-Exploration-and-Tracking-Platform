package com.example.mutualfundexplorationandtrackingplatform.data.remote.mapper

import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.MutualFund
import com.example.mutualfundexplorationandtrackingplatform.data.remote.dto.MutualFundDTO

fun MutualFundDTO.toEntity() = MutualFund(
    schemeCode = schemeCode,
    schemeName = schemeName,
    isinGrowth = isinGrowth,
    isinDivReinvestment = isinDivReinvestment
)