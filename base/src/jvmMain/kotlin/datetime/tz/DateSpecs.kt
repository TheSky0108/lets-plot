package jetbrains.datalore.base.datetime.tz

import jetbrains.datalore.base.datetime.Date
import jetbrains.datalore.base.datetime.Month
import jetbrains.datalore.base.datetime.WeekDay

internal object DateSpecs {
    fun last(weekDay: WeekDay, month: Month): DateSpec {
        return object : DateSpec {

            override val rRule: String
                get() = "RRULE:FREQ=YEARLY;BYDAY=-1" + weekDay.abbreviation + ";BYMONTH=" + (month.ordinal() + 1)

            override fun getDate(year: Int): Date {
                val days = month.getDaysInYear(year)
                for (d in days downTo 1) {
                    val date = Date(d, month, year)
                    if (date.weekDay === weekDay) return date
                }
                throw RuntimeException()
            }
        }
    }

    @JvmOverloads
    fun first(weekDay: WeekDay, month: Month, number: Int = 1): DateSpec {
        return object : DateSpec {

            override val rRule: String
                get() = "RRULE:FREQ=YEARLY;BYDAY=" + number + weekDay.abbreviation + ";BYMONTH=" + (month.ordinal() + 1)

            override fun getDate(year: Int): Date {
                val startDay = (number - 1) * WeekDay.values().size + 1
                val days = month.getDaysInYear(year)
                for (d in startDay..days) {
                    val date = Date(d, month, year)
                    if (date.weekDay === weekDay) return date
                }
                throw RuntimeException()
            }
        }
    }
}