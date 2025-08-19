package com.berlin.remote.network

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

val DEFAULT_START_DATE: String =
    Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date.plus(
        1,
        DateTimeUnit.DAY
    ).toString()

val DEFAULT_END_DATE: String =
    Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date.plus(
        21,
        DateTimeUnit.DAY
    ).toString()