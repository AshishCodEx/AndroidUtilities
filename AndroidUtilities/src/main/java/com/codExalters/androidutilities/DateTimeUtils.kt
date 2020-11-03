package com.codExalters.androidutilities

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextUtils
import android.text.style.RelativeSizeSpan
import android.text.style.SuperscriptSpan
import android.util.Log
import android.widget.TextView
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


/**
 * Created by codexalters on 6/2/18.
 */
object DateTimeUtils {


    const val apiResponseDataTimeFormat = "yyyy-MM-dd HH:mm:ss"
    const val displayDateTimeFormatNumericDate = "dd MMM yyyy, hh:mm a"
    const val displayDateTimeFormat = "E, MMM dd, hh:mm a"
    const val displayDateTimeFormatWithAt = "E, MMM dd at hh:mm a"
    const val displayDateFormat: String = "E, dd MMM"
    const val displayDateFormatWithYear: String = "E, dd MMM yyyy"
    const val displayDateFormatNumericDate: String = "dd-MM-yyyy"

    const val displayTimeFormat = "hh:mm a"


    fun addDateSuffix(date:Int):String{
        return when (date) {
            1, 21, 31 -> "" + date.toString() + "st"
            2, 22 -> "" + date.toString() + "nd"
            3, 23 -> "" + date.toString() + "rd"
            else -> "" + date.toString() + "th"
        }
    }

    class OrdinalSuperscriptFormatter(private val stringBuilder: SpannableStringBuilder) {

        fun format(textView: TextView) {
            val text = textView.text
            val matcher: Matcher = PATTERN.matcher(text)
            stringBuilder.clear()
            stringBuilder.append(text)
            while (matcher.find()) {
                val start: Int = matcher.start()
                val end: Int = matcher.end()
                createSuperscriptSpan(start, end)
            }
            textView.text = stringBuilder
        }

        private fun createSuperscriptSpan(start: Int, end: Int) {
            val superscript = SuperscriptSpan()
            val size =
                RelativeSizeSpan(PROPORTION)
            stringBuilder.setSpan(superscript, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            stringBuilder.setSpan(size, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        companion object {
            private const val SUPERSCRIPT_REGEX = "(?<=\\b\\d{0,10})(st|nd|rd|th)(?=\\b)"
            private val PATTERN: Pattern =
                Pattern.compile(SUPERSCRIPT_REGEX)
            private const val PROPORTION = 1f
        }

    }

    fun isValidSecondTime(dateOne: Date, dateTwo: Date): Boolean {

        val millis = dateOne.time - dateTwo.time
        val hours = millis / (1000 * 60 * 60)
        val mins = millis % (1000 * 60 * 60)

        Log.i("DateTimeUtils", "getDateDifferenceInBoolean : $ " + hours + ":" + mins)
        return hours < 0 || mins < 0
    }

    fun getDateDiffInDays(dateOne: Date, dateTwo: Date): String {

        val timeOne = dateOne.time
        val timeTwo = dateTwo.time
        val oneDay = (1000 * 60 * 60 * 24).toLong()
        var delta = (timeTwo - timeOne) / oneDay

        if (delta > 0) {
            return (delta + 1).toString() + " Days"
        } else {
            delta *= -1
            return "-$delta Days"
        }
    }

    fun getDateDifference(sessionStart: Date?, sessionEnd: Date?): String {
        if (sessionStart == null || sessionEnd == null)
            return ""

        val startDateTime = Calendar.getInstance()
        startDateTime.time = sessionStart

        val endDateTime = Calendar.getInstance()
        endDateTime.time = sessionEnd

        val milliseconds1 = startDateTime.timeInMillis
        val milliseconds2 = endDateTime.timeInMillis
        val diff = milliseconds2 - milliseconds1

        val hours = diff / (60 * 60 * 1000)
        var minutes = diff / (60 * 1000)
        minutes -= 60 * hours
        val seconds = diff / 1000

        return if (hours > 0) {
            hours.toString() + " hours " + minutes + " minutes"
        } else {
            if (minutes > 0)
                minutes.toString() + " minutes"
            else {
                seconds.toString() + " seconds"
            }
        }
    }

    fun getStringFromDate(dateFormat: String, date: Date): String {
        var newString = ""
        /* try {
             var newFormat: SimpleDateFormat? = null
             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                 newFormat = SimpleDateFormat(dateFormat)
             }
             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                 newString = newFormat!!.format(date)
             }

         } catch (e: Exception) {
             e.printStackTrace()
             e.toString()
         }
 */
        try {
            val newFormat = SimpleDateFormat(dateFormat, Locale.ENGLISH)
            newString = newFormat.format(date)
        } catch (e: Exception) {
            Log.e("DateTimeUtils", "getStringFromDate : ${e.printStackTrace()} ")
        }
        return newString
    }


    fun getDateFromString(dateString: String): Date {
        if (TextUtils.isEmpty(dateString)) {
            return Date()
        }
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
        try {
            return format.parse(dateString)
        } catch (e: ParseException) {
            e.printStackTrace()
            return Date()
        }
    }


    fun convertDateFormat(
        dateString: String,
        srcDateFormat: String,
        dstDateFormat: String
    ): String {

        if (TextUtils.isEmpty(dateString)) {
            return ""
        }
        return try {
            val format = SimpleDateFormat(srcDateFormat, Locale.ENGLISH)
            val newFormat = SimpleDateFormat(dstDateFormat, Locale.ENGLISH)

            utcToLocalDate(newFormat.format(format.parse(dateString)), dstDateFormat)
        } catch (e: ParseException) {
            e.printStackTrace()
            dateString
        }

    }

    fun convertDateFormatWithoutUTC(
        dateString: String,
        srcDateFormat: String,
        dstDateFormat: String
    ): String {

        if (TextUtils.isEmpty(dateString)) {
            return ""
        }
        return try {
            val format = SimpleDateFormat(srcDateFormat, Locale.ENGLISH)
            val newFormat = SimpleDateFormat(dstDateFormat, Locale.ENGLISH)

            (newFormat.format(format.parse(dateString)))
        } catch (e: ParseException) {
            e.printStackTrace()
            dateString
        }

    }


    fun utcToLocalDate(dateStr: String, srcFormat: String): String {

        val df = SimpleDateFormat(srcFormat, Locale.ENGLISH)
        df.timeZone = TimeZone.getTimeZone("UTC")
        val date = df.parse(dateStr)
        df.timeZone = TimeZone.getDefault()
        return df.format(date)
    }

    fun pad(value: Int): String {
        return if (value < 10) {
            "0$value"
        } else {
            value.toString()
        }
    }

    fun splitToComponentTime(seconds: Int): Array<String> {

        val hours = seconds / 3600
        var remainder = seconds - hours * 3600
        val mins = remainder / 60
        remainder -= mins * 60
        val secs = remainder

        Log.d(
            "DateTimeUtils",
            "splitToComponentTime : ${Arrays.toString(arrayOf(pad(hours), pad(mins), pad(secs)))} "
        )

        return arrayOf<String>(pad(hours), pad(mins), pad(secs))
    }

    fun getTimeAgoString(dateString: String, srsFormat: String, isSrcDateUTC: Boolean): String {
        try {

            var srcDate = dateString
            if (isSrcDateUTC) {
                srcDate = utcToLocalDate(dateString, srsFormat)
            }
            val cal = Calendar.getInstance()
            val sdf = SimpleDateFormat(srsFormat, Locale.ENGLISH)

            cal.time = sdf.parse(srcDate)

            val calendar = Calendar.getInstance()

            val now = calendar.timeInMillis
            val time = cal.timeInMillis

            val diff = now - time

            val seconds = (diff / 1000).toInt() % 60
            val minutes = (diff / (1000 * 60) % 60).toInt()
            val hours = (diff / (1000 * 60 * 60) % 24).toInt()
            val days = (diff / (1000 * 60 * 60 * 24)).toInt()

            println("$time $now")
            println("$hours hours ago")
            println("$minutes minutes ago")
            println("$seconds seconds ago")
            println("$days days ago")

            return if (days > 0) {
                if (days == 1) {
                    "$days day ago"
                } else {
                    "$days days ago"
                }
            } else if (hours > 0) {
                if (hours == 1) {
                    "$hours hour ago"
                } else {
                    "$hours hours ago"
                }
            } else if (minutes > 0) {
                if (minutes == 1) {
                    "$minutes minute ago"
                } else {
                    "$minutes minutes ago"
                }
            } else if (seconds > 0) {
                if (seconds == 1) {
                    "$seconds second ago"
                } else {
                    "$seconds seconds ago"
                }
            } else {
                srcDate
            }

        } catch (e: ParseException) {
            println(e.toString())
            return dateString
        }

    }


    fun getDateFromTimestamp(timestamp: Long, resultDateFormat: String): String {
        val formatter = SimpleDateFormat(resultDateFormat, Locale.ENGLISH)
        return formatter.format(Date(timestamp))

    }

    fun localToGMT(): Date {
        val date = Date()
        val sdf = SimpleDateFormat(apiResponseDataTimeFormat, Locale.ENGLISH)
        sdf.timeZone = TimeZone.getTimeZone("UTC")

        return Date(sdf.format(date))
    }

    fun localToGMTDateString(): String {
        val date = Date()
        val sdf = SimpleDateFormat(apiResponseDataTimeFormat, Locale.ENGLISH)
        sdf.timeZone = TimeZone.getTimeZone("UTC")

        return sdf.format(date)
    }

    fun gmtToLocalDate(date: Date): Date {
        val timeZone = Calendar.getInstance().timeZone.id
        return Date(date.time + TimeZone.getTimeZone(timeZone).getOffset(date.time))
    }


    class MyDate(
        val year: Int,
        val month: Int,
        val dayOfMonth: Int,
        val hourOfDay: Int,
        val minute: Int,
        val seconds: Int
    ) {
        class MyDateBuilder {

            val calendar = Calendar.getInstance()

            var year: Int = calendar.get(Calendar.YEAR)
            var month: Int = calendar.get(Calendar.MONTH)
            var dayOfMonth: Int = calendar.get(Calendar.DAY_OF_MONTH)
            var hourOfDay: Int = calendar.get(Calendar.HOUR_OF_DAY)
            var minute: Int = calendar.get(Calendar.MINUTE)
            var seconds: Int = calendar.get(Calendar.SECOND)

            fun year(year: Int) = apply { this.year = year }
            fun month(month: Int) = apply { this.month = month }
            fun dayOfMonth(dayOfMonth: Int) = apply { this.dayOfMonth = dayOfMonth }
            fun hourOfDay(hourOfDay: Int) = apply { this.hourOfDay = hourOfDay }
            fun minute(minute: Int) = apply { this.minute = minute }
            fun seconds(seconds: Int) = apply { this.seconds = seconds }

            fun build() = MyDate(
                year,
                month,
                dayOfMonth,
                hourOfDay,
                minute,
                seconds
            )

            override fun toString(): String {
                return "MyDateBuilder(year=$year, month=$month, dayOfMonth=$dayOfMonth, hourOfDay=$hourOfDay, minute=$minute, seconds=$seconds)"
            }

        }

        fun getMyDateInUTC(dateFormat: String): String {

            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth, hourOfDay, minute, seconds)

            val timeZone = TimeZone.getTimeZone("UTC")
            val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.ENGLISH)
            simpleDateFormat.timeZone = timeZone

            return simpleDateFormat.format(calendar.time)
        }

        fun getMyDateInLocal(dateFormat: String): String {
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth, hourOfDay, minute, seconds)
            val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.ENGLISH)
            return simpleDateFormat.format(calendar.time)
        }

        fun getDate(): Date {
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth, hourOfDay, minute, seconds)
            return calendar.time

        }

        override fun toString(): String {
            return "MyDate(year=$year, month=$month, dayOfMonth=$dayOfMonth, hourOfDay=$hourOfDay, minute=$minute, seconds=$seconds)"
        }


    }
}