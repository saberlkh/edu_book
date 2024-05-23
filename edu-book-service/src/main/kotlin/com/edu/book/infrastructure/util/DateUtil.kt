package com.edu.book.infrastructure.util

import org.apache.commons.lang3.time.DateUtils
import org.joda.time.DateTime
import org.joda.time.Duration
import org.joda.time.format.DateTimeFormat
import java.sql.Timestamp
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.ArrayList
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale
import org.apache.commons.lang3.math.NumberUtils

/**
 * @Auther: liukaihua
 * @Date: 2024/3/25 09:57
 * @Description:
 */
class DateUtil: DateUtils() {

    companion object {

        const val PATTREN_DATE = "yyyy-MM-dd"
        const val PATTREN_DATE3 = "yyyy-M"
        const val PATTREN_DATE2 = "yyyy/MM/dd"
        const val PATTREN_PATH_DATE = "yyyy_MM"
        const val PATTREN_DATE_CN = "yyyy年MM月dd日"
        const val PATTREN_TIME = "HH:mm:ss"
        const val PATTREN_TIME2 = "HH:mm"
        const val PATTREN_DATE_TIME = "yyyy-MM-dd HH:mm:ss"
        const val PATTREN_DATE_TIME2 = "yyyy-MM-dd HH:mm"
        const val PATTREN_DATE_TIME3 = "M月d日 HH:mm"
        const val PATTREN_DATE_TIME4 = "yyyy-MM-dd HH:mm:00"
        const val PATTREN_DATE_TIME5 = "MM/dd HH:mm"
        const val PATTREN_DATE_TIME6 = "yyyy.MM.dd HH:mm"
        const val PATTREN_DATE_TIME7 = "MMddHHmmss"
        const val PATTREN_DATE_TIME8 = "yyyy.MM.dd"


        fun getCurrentDateTime(): Date {
            val calNow = Calendar.getInstance()
            return calNow.time
        }

        fun getNowTime(): String {
            val date = Date()
            val dateFormat = SimpleDateFormat(PATTREN_DATE_TIME, Locale.CHINA)
            return dateFormat.format(date)
        }

        /**
         * 日期加月
         */
        fun addMonth(month: Int, date: Date): Date {
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar.add(Calendar.MONTH, month)
            return calendar.time
        }

        fun getNowTime(withSeconds: Boolean): String {
            val date = Date()
            val dateFormat = SimpleDateFormat(PATTREN_DATE_TIME, Locale.CHINA)
            if (!withSeconds) {
                date.seconds = 0
            }
            return dateFormat.format(date)
        }

        fun getTimeStamp(dateTime: String, pattern: String): Long {
            val format = SimpleDateFormat(pattern)
            val date = format.parse(dateTime)
            return date.time
        }

        /**
         * Date --> LocalDate
         */
        fun date2LocalDate(date: Date): LocalDate {
            val simpleDateFormat = SimpleDateFormat(PATTREN_DATE)
            val dateStr = simpleDateFormat.format(date)
            return LocalDate.parse(dateStr)
        }

        /**
         * LocalDate --> Date
         */
        fun localDate2Date(localDate: LocalDate): Date {
            val zone = ZoneId.systemDefault()
            val instant = localDate.atStartOfDay(zone).toInstant()
            return Date.from(instant)
        }

        /**
         * 当前时间减去 numsOfDay 天
         */
        fun minusDays(currentDay: Date, numsOfDay: Int): Date {
            val currentDate = date2LocalDate(currentDay)
            return localDate2Date(currentDate.minusDays(numsOfDay.toLong()))
        }

        fun getToday(): Date? {
            return truncate(Date(), 5)
        }

        fun getTodayEnd(): Date {
            return getDayEnd(Date())
        }

        fun convertToDate(dateString: String?): Date? {
            return try {
                val dateFormat = SimpleDateFormat(PATTREN_DATE, Locale.CHINA)
                dateFormat.parse(dateString)
            } catch (var2: ParseException) {
                null
            }
        }

        fun convertToDate(dateString: String?, pattern: String): Date? {
            return try {
                val dateFormat = SimpleDateFormat(pattern, Locale.CHINA)
                dateFormat.parse(dateString)
            } catch (var2: ParseException) {
                null
            }
        }

        fun checkDateString(dateString: String?): Boolean {
            return convertToDate(dateString) != null
        }

        fun convertToDateTime(dateTimeString: String?): Date? {
            return try {
                val dateFormat = SimpleDateFormat(PATTREN_DATE_TIME, Locale.CHINA)
                dateFormat.parse(dateTimeString)
            } catch (var2: ParseException) {
                null
            }
        }

        fun checkDateTimeString(dateTimeString: String): Boolean {
            return convertToDateTime(dateTimeString) != null
        }

        fun getMonthEnd(year: Int, month: Int): Date? {
            val sb = StringBuffer(10)
            val date: Date?
            if (month < 12) {
                sb.append(Integer.toString(year))
                sb.append("-")
                sb.append(month + 1)
                sb.append("-1")
            } else {
                sb.append(Integer.toString(year + 1))
                sb.append("-1-1")
            }
            date = convertToDate(sb.toString())
            if (date != null) {
                date.time = date.time - 1L
            }
            return date
        }

        fun getMonthEnd(`when`: Date): Date? {
            val calendar = Calendar.getInstance()
            calendar.time = `when`
            val year = calendar.get(1)
            val month = calendar.get(2) + 1
            return getMonthEnd(year, month)
        }

        fun getDayEnd(`when`: Date): Date {
            var date = DateUtils.truncate(`when`, 5)
            date = addDays(date, 1)
            date.time = date.time - 1L
            return date
        }

        fun getDay(`when`: Date): Date {
            return truncate(`when`, 5)
        }

        fun dateStart(`when`: Date): Date {
            return DateUtils.truncate(`when`, Calendar.DATE)
        }

        fun dateEnd(`when`: Date): Date {
            return getDayEnd(`when`)
        }

        fun add(`when`: Date, field: Int, amount: Int): Date {
            val calendar = Calendar.getInstance()
            calendar.time = `when`
            calendar.add(field, amount)
            return calendar.time
        }

        fun addDays(`when`: Date, amount: Int): Date {
            return add(`when`, 6, amount)
        }

        fun addMonths(`when`: Date, amount: Int): Date {
            return add(`when`, 2, amount)
        }

        @Throws(ParseException::class)
        fun getWeekDay(date: String, dateValue: Int, flag: Int): String {
            val calObj = Calendar.getInstance()
            val sfObj = SimpleDateFormat(PATTREN_DATE)
            calObj.time = sfObj.parse(date)
            if (dateValue != 7) {
                if (flag == 1) {
                    calObj.firstDayOfWeek = dateValue
                } else {
                    calObj.firstDayOfWeek = dateValue + 1
                }
            }
            calObj.set(7, dateValue)
            return sfObj.format(calObj.time)
        }

        @Throws(Exception::class)
        fun getMonth(date: String, flag: Int): Date {
            val ca = Calendar.getInstance()
            val sfObj = SimpleDateFormat(PATTREN_DATE)
            ca.time = sfObj.parse(date)
            var rtDate: Date? = null
            if (flag == 1) {
                ca.set(5, 1)
                rtDate = ca.time
            } else {
                ca.set(5, 1)
                ca.add(2, 1)
                ca.add(5, -1)
                rtDate = ca.time
            }

            return rtDate
        }

        @Throws(Exception::class)
        fun getStrMonth(date: String, flag: Int): String {
            val sformatObj = SimpleDateFormat(PATTREN_DATE)
            val returnStr = getMonth(date, flag)
            return sformatObj.format(returnStr)
        }

        @Throws(Exception::class)
        fun calMinutes(date1: Date, date2: Date): Int {
            return ((date1.time - date2.time) / 60000L).toInt()
        }

        fun calDay(date: Date, amount: Int): Date {
            val tempCalen = Calendar.getInstance()
            tempCalen.time = date
            tempCalen.add(5, amount)
            return tempCalen.time
        }

        fun dateAddition(date: Date, additStr: String): Date? {
            var reDate: Date? = null
            val strs = additStr.split("\\:".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if ("m" == strs[0]) {
                reDate = add(date, 12, Integer.parseInt(strs[1]))
            } else if ("h" == strs[0]) {
                reDate = add(date, 11, Integer.parseInt(strs[1]))
            } else if ("d" == strs[0]) {
                reDate = add(date, 5, Integer.parseInt(strs[1]))
            }

            return reDate
        }

        fun getDatesOnWeek(startDate: Date, endDate: Date, day: Int): List<Date> {
            val dates = ArrayList<Date>()
            val cal = Calendar.getInstance()
            val endCal = Calendar.getInstance()
            endCal.time = endDate
            cal.time = startDate
            val starDay = cal.get(7)
            if (starDay != 0 && day != 0 && starDay > day) {
                cal.add(3, 1)
            }
            cal.set(7, day)
            while (cal.compareTo(endCal) <= 0) {
                dates.add(cal.time)
                cal.add(3, 1)
            }
            return dates
        }

        fun getDatesOnDoubleWeek(startDate: Date, endDate: Date, day: Int): List<Date> {
            val dates = ArrayList<Date>()
            val cal = Calendar.getInstance()
            val endCal = Calendar.getInstance()
            endCal.time = endDate
            cal.time = startDate
            val startDay = cal.get(7)
            if (startDay != 0 && day != 0 && startDay > day) {
                cal.add(3, 1)
            }
            cal.set(7, day)
            while (cal.compareTo(endCal) <= 0) {
                dates.add(cal.time)
                cal.add(3, 2)
            }
            return dates
        }

        fun getDatesOnMonth(startDate: Date, endDate: Date, date: Int): List<Date> {
            val dates = ArrayList<Date>()
            val cal = Calendar.getInstance()
            val endCal = Calendar.getInstance()
            endCal.time = endDate
            cal.time = startDate
            val startDate = cal.get(5)
            if (startDate > date) {
                cal.add(2, 1)
            }
            cal.set(5, date)
            while (cal.compareTo(endCal) <= 0) {
                dates.add(cal.time)
                cal.add(2, 1)
            }
            return dates
        }

        fun getDatesBetweenTwoDate(beginDate: Date, endDate: Date): List<Date> {
            val lDate = ArrayList<Date>()
            lDate.add(beginDate)
            val cal = Calendar.getInstance()
            cal.time = beginDate
            val bContinue = true
            while (bContinue) {
                cal.add(5, 1)
                if (endDate.before(cal.time)) {
                    break
                }
                lDate.add(cal.time)
            }
            return lDate
        }

        fun getCurrentWeekNum(startDate: Date, endDate: Date): Int {
            return 0
        }

        @Throws(ParseException::class)
        fun parseDate(dateStr: String, pattern: String): Date {
            val df = SimpleDateFormat(pattern)
            return df.parse(dateStr)
        }

        @Throws(ParseException::class)
        fun parseUSDate(dateStr: String, pattern: String): Date {
            val df = SimpleDateFormat(pattern, Locale.US)
            return df.parse(dateStr)
        }

        @Synchronized
        @Throws(ParseException::class)
        fun formatDate(src: Date, pattern: String): Date? {
            val df = SimpleDateFormat(pattern)
            val dateStr = df.format(src)
            var date: Date? = null
            date = df.parse(dateStr)
            return date
        }

        fun getJavaDay(day: Int): Int {
            return if (day == 6) 0 else day + 1
        }

        fun convertDay2Week(day: Date): String {
            val cal = Calendar.getInstance()
            cal.time = day
            val iday = cal.get(7)
            var dayStr = ""
            when (iday) {
                1 -> {
                    dayStr = "星期天"
                }
                2 -> {
                    dayStr = "星期一"
                }
                3 -> {
                    dayStr = "星期二"
                }
                4 -> {
                    dayStr = "星期三"
                }
                5 -> {
                    dayStr = "星期四"
                }
                6 -> {
                    dayStr = "星期五"
                }
                7 -> {
                    dayStr = "星期六"
                }
            }

            return dayStr
        }

        @Throws(ParseException::class)
        fun getDateDiffNum(d1: String, d2: String): Long {
            val dateFormat = SimpleDateFormat(PATTREN_DATE, Locale.CHINA)
            return getDateDiffNum(dateFormat.parse(d1), dateFormat.parse(d2))
        }

        fun getDateDiffNum(startDate: Date, endDate: Date): Long {
            return (endDate.time - startDate.time) / 86400000L
        }

        fun getCurrentYear(): Long? {
            val cal = Calendar.getInstance()
            return cal.get(Calendar.YEAR).toLong()
        }


        fun getCurrentMonth(): Long? {
            val cal = Calendar.getInstance()
            return cal.get(Calendar.MONTH).toLong()
        }


        fun getCurrentDate(): Long {
            val cal = Calendar.getInstance()
            return cal.get(Calendar.DAY_OF_MONTH).toLong()
        }

        fun getDate(date: Date): Long {
            val cal = Calendar.getInstance()
            cal.time = date
            return cal.get(Calendar.DAY_OF_YEAR).toLong()
        }

        fun formatDateStr(src: Date, pattern: String): String? {
            return try {
                val df = SimpleDateFormat(pattern)
                df.format(src)
            } catch (e: Exception) {
                return null
            }
        }

        fun getNextDay(startTime: Date, n: Int): Date {
            val calendar = GregorianCalendar()
            calendar.time = startTime
            calendar.add(Calendar.DAY_OF_MONTH, n)
            return calendar.time
        }

        fun isOverlap(
            leftStartDate: Date, leftEndDate: Date, rightStartDate: Date, rightEndDate: Date
        ): Boolean {
            return (!leftStartDate.before(rightStartDate) && leftStartDate.before(rightEndDate)
                    || leftStartDate.after(rightStartDate) && !leftStartDate.after(rightEndDate)
                    || !rightStartDate.before(leftStartDate) && rightStartDate.before(leftEndDate)
                    || rightStartDate.after(leftStartDate) && !rightStartDate.after(leftEndDate))
        }

        fun getStrTime(date: Date): String {
            val calendar = Calendar.getInstance()
            val currentYear = calendar.get(Calendar.YEAR)
            val currentDayOfYear = calendar.get(Calendar.DAY_OF_YEAR)
            calendar.time = date
            val year = calendar.get(Calendar.YEAR)
            val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)

            val dateFormat = SimpleDateFormat(PATTREN_TIME, Locale.CHINA)
            val dateTimeFormat = SimpleDateFormat(PATTREN_DATE_TIME, Locale.CHINA)
            return if (year == currentYear && currentDayOfYear == dayOfYear)
                "今天 " + dateFormat.format(date)
            else
                dateTimeFormat.format(date)
        }

        fun getDatesBetweenTwoDates(start: Date, end: Date, increment: Int): List<Date> {
            val lDate = ArrayList<Date>()
            lDate.add(start)
            val cal = Calendar.getInstance()
            if (increment == 11) {
                cal.time = truncate(start, 11)
            } else {
                cal.time = truncate(start, 5)
            }
            while (cal.time.before(end)) {
                if (increment == 3) {
                    while (cal.get(7) != 1) {
                        cal.add(5, 1)
                    }
                }
                cal.add(increment, 1)
                if (cal.time.after(end)) {
                    break
                }
                lDate.add(cal.time)
            }
            lDate.add(end)
            return lDate
        }

        /**
         * 切割时间段
         *
         * @param start
         * @param end
         * @param interval
         * @return
         */
        fun getIntervalTimeList(start: String, end: String, interval: Int): List<String> {
            var startDate = convertToDate(start)
            val endDate = convertToDate(end)
            val list = ArrayList<String>()
            list.add(formatDateStr(startDate!!, PATTREN_DATE2)!!)
            while (startDate!!.time <= endDate!!.time) {
                val calendar = Calendar.getInstance()
                calendar.time = startDate
                calendar.add(Calendar.DATE, interval)
                if (calendar.time.time >= endDate.time) {
                    if (startDate != endDate) {
                        list.add(formatDateStr(endDate, PATTREN_DATE2)!!)
                    }
                    return list
                } else {
                    startDate = calendar.time
                }
                list.add(formatDateStr(startDate, PATTREN_DATE2)!!)
                startDate = getNextDay(startDate, 1)
                list.add(formatDateStr(startDate, PATTREN_DATE2)!!)
            }
            return list
        }

        /**
         * 计算相差天数
         */
        fun calDateDay(date: Date?): Int {
            if (date == null) return NumberUtils.INTEGER_ZERO
            val day = (date.time - Date().time) / (1000 * 3600 * 24)
            return day.toInt()
        }

        /**
         * 判断某个时间是否在给定的时间段范围内
         */
        fun isBetween(targetDateTime: String?, startDateTime: String?, endDateTime: String?): Boolean {
            return try {
                val formatter = DateTimeFormatter.ofPattern(PATTREN_DATE_TIME)
                val targetTime = LocalDateTime.parse(targetDateTime, formatter)
                val startTime = LocalDateTime.parse(startDateTime, formatter)
                val endTime = LocalDateTime.parse(endDateTime, formatter)
                (targetTime.isEqual(startTime) || targetTime.isAfter(startTime)) && (targetTime.isEqual(endTime) || targetTime.isBefore(endTime))
            } catch (e: Exception) {
                false
            }
        }

        /**
         * 判断当前时间是否在给定的时间段范围内
         */
        fun nowIsBetween(startDateTime: String?, endDateTime: String?): Boolean {
            return try {
                val formatter = DateTimeFormatter.ofPattern(PATTREN_DATE_TIME)
                isBetween(LocalDateTime.now().format(formatter), startDateTime, endDateTime)
            } catch (e: Exception) {
                false
            }
        }

        fun isBefore(sourceDateTime: String?, targetDateTime: String?): Boolean {
            return try {
                val formatter = DateTimeFormatter.ofPattern(PATTREN_DATE_TIME)
                LocalDateTime.parse(sourceDateTime).isBefore(LocalDateTime.parse(targetDateTime, formatter))
            } catch (e: Exception) {
                false
            }
        }

        fun isAfter(sourceDateTime: String?, targetDateTime: String?): Boolean {
            return try {
                val formatter = DateTimeFormatter.ofPattern(PATTREN_DATE_TIME)
                LocalDateTime.parse(sourceDateTime).isAfter(LocalDateTime.parse(targetDateTime, formatter))
            } catch (e: Exception) {
                false
            }
        }

        fun nowIsBefore(targetDateTime: String?): Boolean {
            return try {
                val formatter = DateTimeFormatter.ofPattern(PATTREN_DATE_TIME)
                LocalDateTime.now().isBefore(LocalDateTime.parse(targetDateTime, formatter))
            } catch (e: Exception) {
                false
            }
        }

        fun nowIsAfter(targetDateTime: String?): Boolean {
            return try {
                val formatter = DateTimeFormatter.ofPattern(PATTREN_DATE_TIME)
                LocalDateTime.now().isAfter(LocalDateTime.parse(targetDateTime, formatter))
            } catch (e: Exception) {
                false
            }
        }

        fun nowIsAfterOrEqual(targetDateTime: String, haveSeconds: Boolean): Boolean {
            val formatter = DateTimeFormatter.ofPattern(PATTREN_DATE_TIME)
            var target = LocalDateTime.parse(targetDateTime, formatter)
            var now = LocalDateTime.now()
            if (!haveSeconds) {
                target = target.withSecond(0).withNano(0)
                now = now.withSecond(0).withNano(0)
            }
            return now == target || now.isAfter(target)
        }

        /**
         * 计算制定日期距离当前时间相差多少秒
         */
        fun diffSeconds(targetDateTime: String?): Int {
            return try {
                val now = DateTime.now()
                val formatter = DateTimeFormat.forPattern(PATTREN_DATE_TIME)
                val startTime = DateTime.parse(targetDateTime, formatter)
                val seconds = Duration(now, startTime).standardSeconds
                Integer.parseInt(seconds!!.toString())
            } catch (e: Exception) {
                0
            }
        }

        /**
         * 日期加秒
         * @param date 格式：YYYY-MM-dd HH:mm:ss
         */
        fun addSeconds(date: String, seconds: Long?): String {
            val dateTimeFormatter = DateTimeFormatter.ofPattern(PATTREN_DATE_TIME)
            return LocalDateTime.parse(date, dateTimeFormatter).plusSeconds(seconds!!).format(dateTimeFormatter)
        }

        /**
         * long -> yyyy-MM-dd HH:mm:ss
         */
        fun parseLocalDateTime(time: Long): String {
            val dateTimeFormatter = DateTimeFormatter.ofPattern(PATTREN_DATE_TIME)
            return dateTimeFormatter.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()))
        }

        /**
         * long -> yyyy-MM-dd HH:mm:ss
         */
        fun parseLocalDateTime(time: Long, formatPattern: String): String {
            val dateTimeFormatter = DateTimeFormatter.ofPattern(formatPattern)
            return dateTimeFormatter.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()))
        }

        /**
         * 日期加秒
         */
        fun addSeconds(time: Long, seconds: Long): String {
            val dateTimeFormatter = DateTimeFormatter.ofPattern(PATTREN_DATE_TIME)
            val dateTime = dateTimeFormatter.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()))
            return LocalDateTime.parse(dateTime, dateTimeFormatter).plusSeconds(seconds).format(dateTimeFormatter)
        }

        fun parse(targetDate: String, formatPattern: String): String {
            val pattern = getPattrenStr(targetDate)
            return LocalDateTime.parse(targetDate, DateTimeFormatter.ofPattern(pattern))
                .format(DateTimeFormatter.ofPattern(formatPattern))
        }

        fun parse(targetDate: Date, formatPattern: String): String {
            val simpleDateFormat = SimpleDateFormat(formatPattern)
            return simpleDateFormat.format(targetDate)
        }

        private fun getPattrenStr(targetDate: String): String {
            val length = targetDate.length
            val pattern: String
            pattern = when (length) {
                16 -> PATTREN_DATE_TIME2
                19 -> PATTREN_DATE_TIME
                else -> PATTREN_DATE_TIME
            }
            return pattern
        }

        /**
         * 计算两个日期之间的差值并转换成xx小时xx分xx秒
         */
        fun convertTimeToProgressBar(baseDate: String, targetDate: String): String {
            val basePattern = getPattrenStr(baseDate)
            val targetPattern = getPattrenStr(targetDate)
            val baseDateTime = LocalDateTime.parse(baseDate, DateTimeFormatter.ofPattern(basePattern))
            var targetDateTime = LocalDateTime.parse(targetDate, DateTimeFormatter.ofPattern(targetPattern))
            val diffSecond = baseDateTime.second
            if (diffSecond > 0) {
                targetDateTime = targetDateTime.plusSeconds(diffSecond.toLong())
            }
            val duration = java.time.Duration.between(baseDateTime, targetDateTime)
            val diffSeconds = duration.seconds
            return convertSecondsToProgressBar(diffSeconds)
        }

        fun addDaysWithHour(sourceTime: String, days: Int, hour: Int): LocalDateTime {
            val pattern = getPattrenStr(sourceTime)
            val sourceDateTime = LocalDateTime.parse(sourceTime, DateTimeFormatter.ofPattern(pattern))
            return sourceDateTime.plusDays(days.toLong()).withHour(hour).withMinute(0).withSecond(0)
        }

        /**
         * 計算兩個日期相差秒數
         */
        fun diffBetweenTimes(baseDate: String, targetDate: String): Long {
            val basePattern = getPattrenStr(baseDate)
            val targetPattern = getPattrenStr(targetDate)
            val baseDateTime = LocalDateTime.parse(baseDate, DateTimeFormatter.ofPattern(basePattern))
            val targetDateTime = LocalDateTime.parse(targetDate, DateTimeFormatter.ofPattern(targetPattern))
            val duration = java.time.Duration.between(baseDateTime, targetDateTime)
            if (duration.seconds < 0) {
                return 0
            }
            return duration.seconds
        }

        /**
         * 将seconds转换成进度条时间
         */
        fun convertSecondsToProgressBar(diffSeconds: Long): String {
            val hour = diffSeconds / 3600
            val minutes = diffSeconds % 3600 / 60
            val seconds = diffSeconds % 3600 % 60
            var buffer = StringBuffer()
            if (hour > 0) {
                buffer = if (hour.toString().length < 2) buffer.append("0").append(hour) else buffer.append(hour)
                buffer.append(":")
            }
            if (minutes >= 0) {
                buffer = if (minutes.toString().length < 2) buffer.append("0").append(minutes) else buffer.append(minutes)
                buffer.append(":")
            }
            if (seconds >= 0) {
                buffer = if (seconds.toString().length < 2) buffer.append("0").append(seconds) else buffer.append(seconds)
            }
            if (seconds < 0) {
                buffer = buffer.append("00")
            }
            return buffer.toString()
        }

        fun getYesterdayStr(pattern: String): String {
            val yesterday = DateUtil.minusDays(Date(), 1)
            return formatDateStr(yesterday, pattern)!!
        }

        /**
         * 格式化时间
         */
        fun format(dateTime: Date, dateFormatterPattern: String): String {
            val simpleDateFormat = SimpleDateFormat(dateFormatterPattern)
            return simpleDateFormat.format(dateTime)
        }

        /**
         * 获取当前日期的格式化字符串
         */
        fun getCurrentDay(dateFormatterPattern: String): String {
            val currentDay = Date()
            return format(currentDay, dateFormatterPattern)
        }

        /**
         * 获取当前时间的零点时间
         */
        fun getCurrentZeroTime(dateTime: String): String {
            val calendar = Calendar.getInstance()
            calendar.time = parseDate(dateTime, PATTREN_DATE)
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            calendar.add(Calendar.DAY_OF_MONTH, 0)
            return parse(calendar.time, PATTREN_DATE_TIME)
        }

        /**
         * 获取当前零点时间后任意秒数后的时间
         */
        fun getDateTimeAfterSeconds(seconds: Long, pattern: String): String {
            val currentZeroTime = getCurrentZeroTime(getNowTime())
            return parse(addSeconds(currentZeroTime, seconds), pattern)
        }

        /**
         * 计算两个时间戳相隔天数
         */
        fun diffDays(stratTime: Long, endTime: Long): Long {
            return longToLocalDate(stratTime).until(longToLocalDate(endTime), ChronoUnit.DAYS)
        }

        /**
         * long -> localDate
         */
        fun longToLocalDate(time: Long): LocalDate {
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()).toLocalDate()
        }

        /**
         * long -> localDateTime
         */
        fun longToLocalDateTime(time: Long): LocalDateTime {
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault())
        }

        /**
         * localDateTime -> long
         */
        fun localDateTimeTolong(localDateTime: LocalDateTime): Long {
            return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        }

        /**
         * long（年月日） 补全时分秒 -> string
         */
        fun datelongToDateTimeString(date: Long, fillTime: Boolean): String {
            val df = DateTimeFormatter.ofPattern(PATTREN_DATE_TIME)
            val newLocalDateTime = if (fillTime) {
                longToLocalDateTime(date).withHour(23).withMinute(59).withSecond(59).withNano(0)
            } else {
                longToLocalDateTime(date)
            }
            return df.format(newLocalDateTime)
        }

        /**
         * 返回前一天23:59:59 的时间戳
         */
        fun theDayBefore(): Long {
            val theDayBefore = LocalDateTime.now().minusDays(1).withHour(23).withMinute(59).withSecond(59).withNano(0)
            return localDateTimeTolong(theDayBefore)
        }

        /**
         * 获取LocalDateTime的时间戳（毫秒）
         * @param localDateTime 要获取的时间
         * @return 时间戳
         */
        fun getLocalDateTimeMilliSecond(localDateTime: LocalDateTime): Long {
            return localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli()
        }

        /**
         * 延长时间，单位天
         * 返回日期,如2020-03-24
         */
        fun getDateAfterDays(dateTime: String, days: Int): String {
            val nextTime = getNextDay(parseDate(dateTime, PATTREN_DATE), days)
            return parse(nextTime, PATTREN_DATE)
        }

        /**
         * 日期的开始时间 00：00：00
         */
        fun toLocalDateTimeStart(localDate: LocalDate): LocalDateTime {
            val localTime = LocalTime.of(0, 0, 0)
            return LocalDateTime.of(localDate, localTime)
        }

        /**
         * 日期的结束时间 23：59：59
         */
        fun toLocalDateTimeEnd(localDate: LocalDate): LocalDateTime {
            val localTime = LocalTime.of(23, 59, 59)
            return LocalDateTime.of(localDate, localTime)
        }

        fun toDate(localDatetime: LocalDateTime): Date {
            val zone = ZoneId.systemDefault()
            val instant = localDatetime.atZone(zone).toInstant()
            return Date.from(instant)
        }

        fun toLong(localDatetime: LocalDateTime): Long {
            return toDate(localDatetime).time
        }

        fun getMaxTimestamp(time1: Long, time2: Long): Timestamp {
            return Timestamp(Math.max(time1, time2))
        }

        fun getMinTimestamp(time1: Long, time2: Long): Timestamp {
            return Timestamp(Math.min(time1, time2))
        }

        /**
         * Timestamp 转换 String
         * @param time 要获取的时间
         * @param pattern 时间格式
         * @return String类型时间
         */
        fun TimestampToString(time: Timestamp, pattern: String): String {
            val format = SimpleDateFormat(pattern)
            return format.format(time)
        }

    }

}