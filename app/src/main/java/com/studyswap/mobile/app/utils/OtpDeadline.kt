package com.studyswap.mobile.app.utils

import java.time.Duration
import java.time.Instant

fun computeOtpDeadlineWallClockMillis(expiresAtIso: String?, serverTimeIso: String?): Long {
    if (expiresAtIso.isNullOrBlank() || serverTimeIso.isNullOrBlank()) {
        return System.currentTimeMillis() + 5 * 60_000L
    }
    return try {
        val exp = Instant.parse(expiresAtIso)
        val srv = Instant.parse(serverTimeIso)
        val remaining = Duration.between(srv, exp).toMillis().coerceAtLeast(0)
        System.currentTimeMillis() + remaining
    } catch (_: Exception) {
        System.currentTimeMillis() + 5 * 60_000L
    }
}

fun formatCountdownSeconds(totalSeconds: Int): String {
    val m = totalSeconds / 60
    val s = totalSeconds % 60
    return "%d:%02d".format(m, s)
}
