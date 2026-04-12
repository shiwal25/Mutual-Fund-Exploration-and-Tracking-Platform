package com.example.mutualfundexplorationandtrackingplatform.data.cache

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

/**
 * NAV values publish once daily between 9 PM – 11 PM IST.
 * "Fresh" = synced after the most recent 11 PM IST boundary.
 *
 * Examples
 *  Current time 8 PM IST  → boundary is 11 PM IST yesterday → any sync after that is fresh
 *  Current time 11:30 PM IST → boundary is 11 PM IST today → only syncs from tonight are fresh
 */
object CachePolicy {
    // No eager `private val IST` here anymore

    fun isStale(lastSyncEpoch: Long): Boolean {
        if (lastSyncEpoch == 0L) return true

        val ist = ZoneId.of("Asia/Kolkata")        // lazily resolved on first call, on IO
        val now = ZonedDateTime.now(ist)
        val lastSync = Instant.ofEpochMilli(lastSyncEpoch).atZone(ist)

        val todayCutoff = now.toLocalDate().atTime(23, 0).atZone(ist)
        val freshnessBoundary =
            if (now >= todayCutoff) todayCutoff else todayCutoff.minusDays(1)

        return lastSync < freshnessBoundary
    }

    fun now(): Long = System.currentTimeMillis()
}