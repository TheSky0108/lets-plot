package jetbrains.datalore.visualization.plot.gog.common.text

import jetbrains.datalore.base.numberFormat.NumberFormatUtil
import jetbrains.datalore.visualization.plot.gog.common.data.DataType
import jetbrains.datalore.visualization.plot.gog.common.time.interval.TimeInterval
import java.util.function.Function

object Formatter {
    private val SCI_NOTATION_EXP_REGEX = Regex("(.+)([eE][0-9]+)(.*)")

    private val DEF_NUMBER_FORMATTER = Function<Any, String> { input ->
        val number = input as Number
        NumberFormatUtil.formatNumber(number, "#,###.##")
    }

    fun time(pattern: String): Function<Any, String> {
        return Function { input -> DateTimeFormatUtil.formatDateUTC(input as Number, pattern) }
    }

    @JvmOverloads
    fun number(pattern: String, useMetricPrefix: Boolean = false): Function<Any, String> {
        return Function { input ->
            var result = "NaN"
            if (input is Number) {
                var s = NumberFormatUtil.formatNumber(input as Number, pattern)
                if (useMetricPrefix) {
                    s = replaceExponentWithMetricPrefix(s)
                }
                result = s
            }
            result
        }
    }

    private fun replaceExponentWithMetricPrefix(number: String): String {
        val result = SCI_NOTATION_EXP_REGEX.find(number)
                ?: // not a scientific notation
                return number

        val groups = result.groups
        if (groups.size != 4) {
            // strange
            return number
        }

        val exp = result.groupValues[2]
        val metricPrefix: String
        metricPrefix = when (exp.toUpperCase()) {
            "E0" -> ""
            "E3" -> "k"
            "E6" -> "M"
            "E9" -> "G"
            "E12" -> "T"
            "E18" -> "E"
            else -> return number
        }
        return result.groupValues[1] + metricPrefix + result.groupValues[3]
    }

    fun legend(dataType: DataType): Function<Any?, String> {
        return tooltip(dataType)
    }

    fun tooltip(dataType: DataType): Function<Any?, String> {
        return nullable(tooltipImpl(dataType), "null")
    }

    private fun tooltipImpl(dataType: DataType): Function<Any, String> {
        return when (dataType) {
            DataType.NUMBER -> DEF_NUMBER_FORMATTER
            DataType.STRING -> Function { it.toString() } // no formatting really (toSting)
            DataType.INSTANT -> time(DateTimeFormatUtil.DATE_MEDIUM_TIME_SHORT)
            DataType.INSTANT_OF_DAY -> time(DateTimeFormatUtil.DATE_MEDIUM)
            DataType.INSTANT_OF_MONTH -> time(DateTimeFormatUtil.YEAR_MONTH)
            DataType.INSTANT_OF_QUARTER, DataType.INSTANT_OF_HALF_YEAR -> time(DateTimeFormatUtil.YEAR_QUARTER)
            DataType.INSTANT_OF_YEAR -> time(DateTimeFormatUtil.YEAR)
        }
    }

    fun tableCell(dataType: DataType): Function<Any?, String> {
        return tableCell(dataType, "null")
    }

    private fun tableCell(dataType: DataType, nullString: String): Function<Any?, String> {
        return nullable(tableCellImpl(dataType), nullString)
    }

    private fun tableCellImpl(dataType: DataType): Function<Any, String> {
        when (dataType) {
            DataType.NUMBER -> return DEF_NUMBER_FORMATTER
            DataType.STRING -> return Function { it.toString() } // no formatting really (toSting)
            DataType.INSTANT -> return time("EEE, MMM d, ''yy")
            else -> if (dataType.isTimeInterval) {
                val timeInterval = TimeInterval.fromIntervalDataType(dataType)
                return timeInterval.tickFormatter
            }
        }

        throw IllegalArgumentException("Can't create formatter for data type $dataType")
    }

    private fun nullable(f: Function<Any, String>, nullString: String): Function<Any?, String> {
        return Function { input ->
            if (input == null) {
                return@Function nullString
            }
            f.apply(input)
        }
    }

    fun ordinalSeries(dataType: DataType): Function<Any?, String> {
        return tableCell(dataType)
    }
}