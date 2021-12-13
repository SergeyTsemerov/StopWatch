package com.sergeytsemerov.stopwatch.model

interface TimestampProvider {
    fun getMilliseconds(): Long
}