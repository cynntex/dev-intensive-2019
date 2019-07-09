package ru.skillbranch.devintensive.extensions

import java.lang.IllegalStateException
import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR


fun Date.format(pattern:String = "HH:mm:ss dd.MM.yy"):String {
    val dateFormat = SimpleDateFormat (pattern, Locale("ru"))
    return dateFormat.format(this)

}

fun Date.add (value: Int, units: TimeUnits = TimeUnits.SECOND): Date {

    var time = this.time

    time += when (units){
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR-> value * HOUR
        TimeUnits.DAY-> value * DAY


    }

    this.time = time
    return this
}


fun Date.humanizeDiff(date: Date = Date()):String {

    val diff = date.time - this.time
    val absDiff = Math.abs(diff)
    val isPast = diff > 0

    return when {
        absDiff / SECOND <= 1 -> "только что"
        absDiff / SECOND <= 45 -> if (isPast) "несколько секунд назад" else "через несколько секунд"
        absDiff / SECOND <= 75 -> if (isPast) "минуту назад" else "через минуту"
        absDiff / MINUTE <= 45 -> if (isPast) "${TimeUnits.MINUTE.plural((absDiff / MINUTE).toInt())} назад"
        else "через ${TimeUnits.MINUTE.plural((absDiff / MINUTE).toInt())}"
        absDiff / MINUTE <= 75 -> if (isPast) "час назад" else "через час"
        absDiff / HOUR <= 22 -> if (isPast) "${TimeUnits.HOUR.plural((absDiff / HOUR).toInt())} назад"
        else "через ${TimeUnits.HOUR.plural((absDiff / HOUR).toInt())}"
        absDiff / HOUR <= 26 -> if (isPast) "день назад" else "через день"
        absDiff / DAY <= 360 -> if (isPast) "${TimeUnits.DAY.plural((absDiff / DAY).toInt())} назад"
        else "через ${TimeUnits.DAY.plural((absDiff / DAY).toInt())}"
        else -> if (isPast) "более года назад" else "более чем через год"
    }

}


enum class TimeUnits(
    private val valuesTime: Long,
    private val forms: List<String>
) {
    SECOND(1000, listOf("секунду","секунды","секунд")),
    MINUTE(1000*60, listOf("минуту","минуты","минут")),
    HOUR(1000*60*60, listOf("час","часа","часов")),
    DAY(1000*60*60*24, listOf("день","дня","дней"));

    fun plural (value: Int): String {
//        $n = abs($n) % 100;
//        $n1 = $n % 10;
//        if ($n > 10 && $n < 20) return $form5;
//        if ($n1 > 1 && $n1 < 5) return $form2;
//        if ($n1 == 1) return $form1;
//        return $form5;
        val n = Math.abs(value)%100
        val n1 = value%10
        if (n in 11..19) return "$value ${forms[2]}"
        if (n1 in 2..4) return "$value ${forms[1]}"
        if(n1==1) return "$value ${forms[0]}"
        return "$value ${forms[2]}"


    }
}