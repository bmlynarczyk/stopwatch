package eu.introsoft.stopwatch


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test


class StopwatchServiceTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun should_change_color_when_10_seconds_to_lap_end() {
//        given
        val preferences = StopwatchPreferences(10, 15, 15)
        val stopwatchValue = MutableLiveData<String>()
        val stopwatchColor = MutableLiveData<String>()
        val stopwatchLaps = MutableLiveData<List<String>>()
        val service = StopwatchService(stopwatchValue, stopwatchColor, stopwatchLaps, preferences)
//        when
        repeat(11) {
            service.count()
        }
//        then
        assertEquals("#0080C0", stopwatchColor.value)
        assertEquals("00:01", stopwatchValue.value)
//        and when
        repeat(4) {
            service.count()
        }
//        then
        assertEquals("#FF8040", stopwatchColor.value)
        assertEquals("00:05", stopwatchValue.value)
    }

    @Test
    fun should_return_to_normal_color_at_break() {
//        given
        val preferences = StopwatchPreferences(10, 15, 15)
        val stopwatchValue = MutableLiveData<String>()
        val stopwatchColor = MutableLiveData<String>()
        val stopwatchLaps = MutableLiveData<List<String>>()
        val service = StopwatchService(stopwatchValue, stopwatchColor, stopwatchLaps, preferences)
//        when
        repeat(27) {
            service.count()
        }
//        then
        assertEquals("#0080C0", stopwatchColor.value)
        assertEquals("00:17", stopwatchValue.value)
    }

    @Test
    fun should_change_color_when_10_seconds_to_break_end() {
//        given
        val preferences = StopwatchPreferences(10, 15, 15)
        val stopwatchValue = MutableLiveData<String>()
        val stopwatchColor = MutableLiveData<String>()
        val stopwatchLaps = MutableLiveData<List<String>>()
        val service = StopwatchService(stopwatchValue, stopwatchColor, stopwatchLaps, preferences)
//        when
        repeat(31) {
            service.count()
        }
//        then
        assertEquals("#FF8040", stopwatchColor.value)
        assertEquals("00:21", stopwatchValue.value)
    }

    @Test
    fun should_return_to_normal_color_after_break() {
//        given
        val preferences = StopwatchPreferences(10, 15, 15)
        val stopwatchValue = MutableLiveData<String>()
        val stopwatchColor = MutableLiveData<String>()
        val stopwatchLaps = MutableLiveData<List<String>>()
        val service = StopwatchService(stopwatchValue, stopwatchColor, stopwatchLaps, preferences)
//        when
        repeat(40) {
            service.count()
        }
//        then
        assertEquals("#0080C0", stopwatchColor.value)
        assertEquals("00:30", stopwatchValue.value)
    }

    @Test
    fun should_set_notification_color_during_delay() {
//        given
        val preferences = StopwatchPreferences(10, 15, 15)
        val stopwatchValue = MutableLiveData<String>()
        val stopwatchColor = MutableLiveData<String>()
        val stopwatchLaps = MutableLiveData<List<String>>()
        val service = StopwatchService(stopwatchValue, stopwatchColor, stopwatchLaps, preferences)
//        when
        repeat(1) {
            service.count()
        }
//        then
        assertEquals("#FF8040", stopwatchColor.value)
        assertEquals("-00:09", stopwatchValue.value)
//        when
        repeat(10) {
            service.count()
        }
//        then
        assertEquals("#0080C0", stopwatchColor.value)
        assertEquals("00:01", stopwatchValue.value)
    }

    @Test
    fun should_print_delayed_stopwatch_value() {
//        given
        val preferences = StopwatchPreferences(10, 15, 15)
        val stopwatchValue = MutableLiveData<String>()
        val stopwatchColor = MutableLiveData<String>()
        val stopwatchLaps = MutableLiveData<List<String>>()
//        when
        val service = StopwatchService(stopwatchValue, stopwatchColor, stopwatchLaps, preferences)
//        then
        assertEquals("#FF8040", stopwatchColor.value)
        assertEquals("-00:10", stopwatchValue.value)
//        when
        repeat(10) {
            service.count()
        }
//        then
        assertEquals("#0080C0", stopwatchColor.value)
        assertEquals("00:00", stopwatchValue.value)
    }

    @Test
    fun should_add_each_lap_and_break_to_stopwatch_laps() {
//        given
        val preferences = StopwatchPreferences(10, 15, 15)
        val stopwatchValue = MutableLiveData<String>()
        val stopwatchColor = MutableLiveData<String>()
        val stopwatchLaps = MutableLiveData<List<String>>()
//        when
        val service = StopwatchService(stopwatchValue, stopwatchColor, stopwatchLaps, preferences)
//        then
        repeat(71) {
            service.count()
        }
//        then
        assertEquals(4, stopwatchLaps.value!!.size)
    }

}