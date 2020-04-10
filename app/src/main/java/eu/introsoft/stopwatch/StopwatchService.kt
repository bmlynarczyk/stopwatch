package eu.introsoft.stopwatch

import androidx.lifecycle.MutableLiveData

class StopwatchService(
    private val stopwatchValue: MutableLiveData<String>,
    private val stopwatchColor: MutableLiveData<String>,
    private val stopwatchLaps: MutableLiveData<List<String>>,
    private val stopwatchPreferences: StopwatchPreferences,
    private val laps: MutableList<String> = ArrayList(),
    private var secondsCount: Int = 0,
    private var minutesCount: Int = 0,
    private var isBreak: Boolean = false,
    private var isLap: Boolean = true,
    private var isDelayed: Boolean = false
) {

    private var nextLapSecond: Int = 0
    private var nextBreakSecond: Int = 0
    private var totalSecondsCounter: Int = 0

    init {
        if (stopwatchPreferences.delay > 0){
            stopwatchColor.postValue("#FF8040")
            isDelayed = true
        } else {
            stopwatchColor.postValue("#0080C0")
        }
        nextBreakSecond = totalSecondsCounter + stopwatchPreferences.lapTime
        nextLapSecond = totalSecondsCounter + stopwatchPreferences.breakTime
        secondsCount -= stopwatchPreferences.delay
        totalSecondsCounter -= stopwatchPreferences.delay
        stopwatchValue.postValue(minusInFront(secondsCount) + asText(minutesCount) + ":" + asText(secondsCount))
    }

    fun count() {
        totalSecondsCounter++
        secondsCount++
        if(totalSecondsCounter == 0){
            stopwatchColor.postValue("#0080C0")
            isDelayed = false
        }
        if (secondsCount == 60) {
            secondsCount = 0
            minutesCount++
        }
        val counterText = minusInFront(secondsCount) + asText(minutesCount) + ":" + asText(secondsCount)
        if(!isDelayed){
            if ((isBreak && totalSecondsCounter == nextLapSecond - 10) || (isLap && totalSecondsCounter == nextBreakSecond - 10)) {
                stopwatchColor.postValue("#FF8040")
            }
            if (isLap && totalSecondsCounter == nextBreakSecond) {
                nextLapSecond = totalSecondsCounter + stopwatchPreferences.breakTime
                stopwatchColor.postValue("#0080C0")
                laps.add(counterText)
                isBreak = true
                isLap = false
                stopwatchLaps.postValue(laps)
            }
            if (isBreak && totalSecondsCounter == nextLapSecond) {
                nextBreakSecond = totalSecondsCounter + stopwatchPreferences.lapTime
                stopwatchColor.postValue("#0080C0")
                laps.add(counterText)
                isBreak = false
                isLap = true
                stopwatchLaps.postValue(laps)
            }
        }
        stopwatchValue.postValue(counterText)
    }

    private fun minusInFront(count: Int): String {
        return if (count < 0)
            "-"
        else
            ""
    }

    private fun asText(count: Int): String {
        var printableCount = count
        if (printableCount < 0)
            printableCount *= -1
        return if (printableCount < 10)
            "0$printableCount"
        else
            "$printableCount"
    }

}