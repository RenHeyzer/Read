package com.example.read.common.utils

import java.util.Date
import javax.inject.Inject

class TimeDifference @Inject constructor() {

    fun getTimeDifference(startDate: Date, endDate: Date): String? {
        if (startDate.time != 0L) {
            var different = endDate.time - startDate.time

            val secondsInMilli: Long = 1000
            val minutesInMilli = secondsInMilli * 60
            val hoursInMilli = minutesInMilli * 60
            val daysInMilli = hoursInMilli * 24
            val monthInMilli = daysInMilli * 28
            val yearInMilli = monthInMilli * 12

            val elapsedYears = different / yearInMilli
            different %= yearInMilli
            val elapsedMonths = different / monthInMilli
            different %= monthInMilli
            val elapsedDays = different / daysInMilli
            different %= daysInMilli
            val elapsedHours = different / hoursInMilli
            different %= hoursInMilli
            val elapsedMinutes = different / minutesInMilli
            different %= minutesInMilli
            val elapsedSeconds = different / secondsInMilli

            return if (elapsedSeconds.toInt() > 0 && elapsedMinutes.toInt() == 0) {
                "несколько секнуд назад"
            } else if (elapsedMinutes.toInt() > 0 && elapsedHours.toInt() == 0) {
                "несколько минут назад"
            } else if (elapsedHours.toInt() > 0 && elapsedDays.toInt() == 0) {
                correctTimeNaming(TimeType.HOUR, elapsedHours.toInt())
            } else if (elapsedDays.toInt() > 0 && elapsedMonths.toInt() == 0) {
                correctTimeNaming(TimeType.DAY, elapsedDays.toInt())
            } else if (elapsedMonths.toInt() > 0 && elapsedYears.toInt() == 0) {
                correctTimeNaming(TimeType.MONTH, elapsedMonths.toInt())
            } else {
                correctTimeNaming(TimeType.YEAR, elapsedYears.toInt())
            }
        } else {
            return null
        }
    }

    private fun correctTimeNaming(timeType: TimeType, time: Int): String {
        when (timeType) {
            TimeType.HOUR -> {
                return when (time) {
                    1 -> {
                        "час назад"
                    }

                    in 2..4 -> {
                        "$time часа назад"
                    }

                    in 5..20 -> {
                        "$time часов назад"
                    }

                    21 -> {
                        "$time час назад"
                    }

                    else -> {
                        "$time часа назад"
                    }
                }
            }

            TimeType.DAY -> {
                return when (time) {
                    1 -> {
                        "$time день назад"
                    }

                    in 2..4 -> {
                        "$time дня назад"
                    }

                    in 5..20 -> {
                        "$time дней назад"
                    }

                    21 -> {
                        "$time день назад"
                    }

                    in 22..24 -> {
                        "$time дня назад"
                    }

                    else -> {
                        "$time дней назад"
                    }
                }
            }

            TimeType.MONTH -> {
                return when (time) {
                    1 -> {
                        "$time месяц назад"
                    }

                    in 2..4 -> {
                        "$time месяца назад"
                    }

                    else -> {
                        "$time месяцев назад"
                    }
                }
            }

            TimeType.YEAR -> {
                return when (time) {
                    1 -> {
                        "$time год назад"
                    }

                    in 2..4 -> {
                        "$time года назад"
                    }

                    in 5..20 -> {
                        "$time лет назад"
                    }
                    else -> {
                        if (time.toString().contains('1')) {
                            "$time год назад"
                        } else if (time.toString().last().toString().toInt() < 5) {
                            "$time года назад"
                        } else {
                            "$time лет назад"
                        }
                    }
                }
            }
        }
    }

    enum class TimeType {
        HOUR,
        DAY,
        MONTH,
        YEAR
    }
}