package com.sergeytsemerov.stopwatch.domain

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class StopwatchListOrchestrator(
    private val stopwatchStateHolder: StopwatchStateHolder,
    private val scope: CoroutineScope,
) {

    private var job: Job? = null
    private val mutableTicker = MutableStateFlow(EMPTY_VALUE)
    val ticker: StateFlow<String> = mutableTicker

    fun start() {
        startJob()
        stopwatchStateHolder.start()
    }

    private fun startJob() {
        job = scope.launch {
            while (isActive) {
                mutableTicker.value = stopwatchStateHolder.getStringTimeRepresentation()
                delay(DELAY_VALUE)
            }
        }
    }

    fun pause() {
        stopwatchStateHolder.pause()
        stopJob()
    }

    fun stop() {
        stopwatchStateHolder.stop()
        stopJob()
        clearValue()
    }

    private fun stopJob() {
        job?.cancel()
    }

    private fun clearValue() {
        mutableTicker.value = DEFAULT_TIME
    }

    companion object {
        private const val EMPTY_VALUE = ""
        private const val DELAY_VALUE: Long = 20
        private const val DEFAULT_TIME = "00:00:000"
    }
}
