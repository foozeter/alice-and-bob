package com.foureyedstraighthair.aliceandbob

import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.util.*



/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun time() {

        val format = SimpleDateFormat("yyyy年MM月dd日 HH時mm分ss.SSS秒")

//        val timeInMillis = 1546700502420L
        val zoneTokyo = "Asia/Tokyo"
        val zoneHawai = "US/Hawaii"
        val zoneNY = "America/Los_Angeles"
        val zoneLondon = "Europe/London"

        val calendar = Calendar.getInstance()

        calendar.timeZone = TimeZone.getTimeZone(zoneTokyo)
        print(calendar)
        calendar.timeZone = TimeZone.getTimeZone(zoneHawai)
        print(calendar)
        calendar.timeZone = TimeZone.getTimeZone(zoneNY)
        print(calendar)
        calendar.timeZone = TimeZone.getTimeZone(zoneLondon)
        print(calendar)
    }

    private fun print(calendar: Calendar) {
        println("##########################################")
        System.out.println("タイムゾーンID : " + calendar.timeZone.id)//[7]
        System.out.println("タイムゾーン : " + calendar.timeZone.displayName)//[8]
        System.out.println(("日時　: " + calendar.get(Calendar.YEAR) + "/"
                + (calendar.get(Calendar.MONTH) + 1) + "/"
                + calendar.get(Calendar.DAY_OF_MONTH) + "　"
                + calendar.get(Calendar.HOUR_OF_DAY) + ":"
                + calendar.get(Calendar.MINUTE) + ":"
                + calendar.get(Calendar.SECOND)))//[9]
    }

    @Test
    fun foo() {
        val t = System.currentTimeMillis().toString(16)
        println("t = $t")
    }
}
