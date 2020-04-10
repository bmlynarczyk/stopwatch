package eu.introsoft.stopwatch

data class StopwatchPreferences(
    val delay: Int = 90,
    val lapTime: Int = 90,
    val breakTime: Int = 60
)