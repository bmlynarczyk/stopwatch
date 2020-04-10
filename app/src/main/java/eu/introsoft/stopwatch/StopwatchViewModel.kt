package eu.introsoft.stopwatch

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import io.reactivex.Observable.interval
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class StopwatchViewModel(application: Application) : AndroidViewModel(application) {

    val stopwatchValue: MutableLiveData<String> = MutableLiveData()
    val stopwatchColor: MutableLiveData<String> = MutableLiveData()
    val stopwatchLaps: MutableLiveData<List<String>> = MutableLiveData()
    private var isStarted = false
    private val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(application.applicationContext)
    private val stopwatchPreferences = StopwatchPreferences(
        delay = sharedPreferences.getString("preferenceDelay", "10")!!.toInt(),
        lapTime = sharedPreferences.getString("preferenceLap", "90")!!.toInt(),
        breakTime = sharedPreferences.getString("preferenceBreak", "60")!!.toInt()
    )
    private val service: StopwatchService = StopwatchService(stopwatchValue, stopwatchColor, stopwatchLaps, stopwatchPreferences)

    private val disposables: CompositeDisposable = CompositeDisposable()

    fun start() {
        if (isStarted) {
            return
        }
        isStarted = true
        disposables.add(
            interval(
                1,
                TimeUnit.SECONDS
            )
                .subscribeOn(Schedulers.computation())
                .subscribe { service.count() }
        )
    }

    fun pause() {
        disposables.clear()
        isStarted = false
    }

    override fun onCleared() {
        disposables.clear()
    }

}