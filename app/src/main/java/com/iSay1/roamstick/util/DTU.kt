package com.iSay1.roamstick.util

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.EditText
import androidx.annotation.RequiresApi
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

public class DTU {

    var currentHour = 0
    var currentMinute: Int = 0
    var currentSeconds: Int = 0
    var currentYear = 0
    var currentMonth: Int = 0
    var currentDay: Int = 0

    val FLAG_ONLY_NEW = 1
    val FLAG_OLD_AND_NEW = 2
//    public static final int FLAG_OLD_AND_NEW = 2;

    val DATE_FORMAT = "dd-MMM-yyyy HH:mm:ss"
    val YYYY_MM_DD_HMS = "yyyy-MM-dd HH:mm:ss"
    val MM_DD_YYY_HH_MM = "MM/dd/yyyy HH:mm"
    val YYYY_MM_DD_T_HMS_Z = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"


    fun showDatePickerDialog(context: Context?, dateFlg: Int, dateEditText: EditText) {

        // Displays Date picker
        val c = Calendar.getInstance()
        currentYear = c[Calendar.YEAR]
        currentMonth = c[Calendar.MONTH]
        currentDay = c[Calendar.DAY_OF_MONTH]
        val datepicker = DatePickerDialog(
            context!!,  /*R.style.datepicker*/AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,
            { view, selectedYear, monthOfYear, dayOfMonth ->
                if (dateFlg == FLAG_ONLY_NEW) {
                    if (selectedYear != currentYear || monthOfYear < currentMonth && selectedYear == currentYear || dayOfMonth < currentDay && selectedYear == currentYear && monthOfYear <= currentMonth) {
                        // showToastShort(appContext, "ECS",
                        // "Please select proper date.");
                        dateEditText.setText(getCurrentDateTimeStamp(""))
                    } else {
                        val date_selected: String
                        date_selected =
                            if (monthOfYear >= 0 && monthOfYear < 9 && dayOfMonth > 0 && dayOfMonth < 10) ("0"
                                    + dayOfMonth.toString() + "-0"
                                    + (monthOfYear + 1).toString() + "-"
                                    + selectedYear.toString()) else if (monthOfYear >= 0 && monthOfYear < 9) (dayOfMonth.toString() + "-0"
                                    + (monthOfYear + 1).toString() + "-"
                                    + selectedYear.toString()) else if (dayOfMonth > 0 && dayOfMonth < 10) ("0"
                                    + dayOfMonth.toString() + "-"
                                    + (monthOfYear + 1).toString() + "-"
                                    + selectedYear.toString()) else (dayOfMonth.toString() + "-"
                                    + (monthOfYear + 1).toString() + "-"
                                    + selectedYear.toString())
                        dateEditText.setText(date_selected)
                    }
                } else if (dateFlg == FLAG_OLD_AND_NEW) {
                    val date_selected: String
                    date_selected =
                        if (monthOfYear >= 0 && monthOfYear < 9 && dayOfMonth > 0 && dayOfMonth < 10) ("0"
                                + dayOfMonth.toString() + "-0"
                                + (monthOfYear + 1).toString() + "-"
                                + selectedYear.toString()) else if (monthOfYear >= 0 && monthOfYear < 9) (dayOfMonth.toString() + "-0"
                                + (monthOfYear + 1).toString() + "-"
                                + selectedYear.toString()) else if (dayOfMonth > 0 && dayOfMonth < 10) ("0"
                                + dayOfMonth.toString() + "-"
                                + (monthOfYear + 1).toString() + "-"
                                + selectedYear.toString()) else dayOfMonth.toString() + "-" + (monthOfYear + 1).toString() + "-" + selectedYear.toString()
                    dateEditText.setText(date_selected)
                }
            }, currentYear, currentMonth, currentDay
        )
        datepicker.show()
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    fun get_yyyy_mm_dd(timeZoneDate: String?): String {
        val originalFormat = SimpleDateFormat("dd-MM-yyyy")
        val targetFormat = SimpleDateFormat("yyyy-MM-dd")
        var date: Date? = null
        try {
            date = originalFormat.parse(timeZoneDate)
            println("Old Format :   " + originalFormat.format(date))
            println("New Format :   " + targetFormat.format(date))
        } catch (ex: ParseException) {
            // Handle Exception.
        }
        return targetFormat.format(date)
    }


    fun getCurrentDateTimeStamp(format: String?): String? {
        val dateFormatter: DateFormat = SimpleDateFormat(format)
        dateFormatter.isLenient = false
        val today = Date()
        val s = dateFormatter.format(today)
        Log.e("today_Date", "" + s)
        return s
    }

    fun getCurrentDateTime(format: String?): String? {
        val dateFormatter: DateFormat = SimpleDateFormat(format)
        dateFormatter.isLenient = false
        dateFormatter.timeZone = Calendar.getInstance().timeZone
        val today = Date()
        val s = dateFormatter.format(today)
        Log.e("today_Date", "" + s)
        return s
    }

}