package com.example.mutualfundexplorationandtrackingplatform.data.cache

import java.time.Instant
import java.time.ZoneId

object CachePolicy {

    fun isStale(lastSyncEpoch: Long): Boolean {
        if (lastSyncEpoch == 0L) return true

        val instant = Instant.ofEpochMilli(System.currentTimeMillis())
        val now = instant.atZone(ZoneId.of("Asia/Kolkata"))

        val todayCutoff = now
            .minusDays(1)
            .withHour(23)
            .withMinute(0)
            .withSecond(0)
            .withNano(0)
            .toInstant()
            .toEpochMilli()

        return lastSyncEpoch < todayCutoff
    }
}