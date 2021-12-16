package com.sergeytsemerov.stopwatch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.sergeytsemerov.stopwatch.domain.ElapsedTimeCalculator
import com.sergeytsemerov.stopwatch.domain.StopwatchListOrchestrator
import com.sergeytsemerov.stopwatch.domain.StopwatchStateCalculator
import com.sergeytsemerov.stopwatch.domain.StopwatchStateHolder
import com.sergeytsemerov.stopwatch.model.TimestampProvider
import com.sergeytsemerov.stopwatch.utils.TimestampMillisecondsFormatter
import kotlinx.coroutines.cancel

class MainViewModel : ViewModel() {

    private val timestampProvider = object : TimestampProvider {
        override fun getMilliseconds(): Long {
            return System.currentTimeMillis()
        }
    }

    private val stopwatchListOrchestrator = StopwatchListOrchestrator(
        StopwatchStateHolder(
            StopwatchStateCalculator(
                timestampProvider,
                ElapsedTimeCalculator(timestampProvider)
            ),
            ElapsedTimeCalculator(timestampProvider),
            TimestampMillisecondsFormatter()
        ),
        viewModelScope
    )

    val ticker: LiveData<String> = stopwatchListOrchestrator.ticker.asLiveData()

    fun buttonStart() {
        stopwatchListOrchestrator.start()
    }

    fun buttonPause() {
        stopwatchListOrchestrator.pause()
    }

    fun buttonStop() {
        stopwatchListOrchestrator.stop()
    }

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }
}