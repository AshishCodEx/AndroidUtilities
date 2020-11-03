package com.codExalters.androidutilities

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import java.util.*


/**
 * Created by codexalters on 13/2/18.
 */
object DateTimePickerUtils {

    fun showDatePicker(context: Activity, listener: DatePickerDialog.OnDateSetListener) {

        val pastDate: Calendar = Calendar.getInstance()
        val futureDate: Calendar = Calendar.getInstance()
        val currentDate: Calendar = Calendar.getInstance()

        // min date
        pastDate.set(Calendar.MILLISECOND, pastDate.getMinimum(Calendar.MILLISECOND))
        pastDate.add(Calendar.YEAR, -5)
        val minDate = pastDate.time.time

        //max date
        futureDate.set(Calendar.MILLISECOND, futureDate.getMaximum(Calendar.MILLISECOND))
        futureDate.add(Calendar.YEAR, 5)
        val maxDate = futureDate.time.time

        val datePickerDialog = DatePickerDialog(
            context,
            listener,
            currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = minDate
        datePickerDialog.datePicker.maxDate = maxDate



        datePickerDialog.show()
    }


    fun showTimePicker(context: Activity, listener: TimePickerDialog.OnTimeSetListener) {

        val mCurrentTime = Calendar.getInstance()
        val hour = mCurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mCurrentTime.get(Calendar.MINUTE)
        val mTimePicker: TimePickerDialog
        mTimePicker = TimePickerDialog(
            context,
            listener, hour, minute, false
        )//true for 24 hour time
        mTimePicker.setTitle("Select Time")
        mTimePicker.show()


    }

    fun showTodayDatePicker(context: Activity, listener: DatePickerDialog.OnDateSetListener) {

        val pastDate: Calendar = Calendar.getInstance()
        val futureDate: Calendar = Calendar.getInstance()
        val currentDate: Calendar = Calendar.getInstance()

        val minDate = pastDate.time.time

        //max date
        futureDate.set(Calendar.MILLISECOND, futureDate.getMaximum(Calendar.MILLISECOND))
        futureDate.add(Calendar.YEAR, 5)
        val maxDate = futureDate.time.time

        val datePickerDialog = DatePickerDialog(
            context,
            listener,
            currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = minDate
        datePickerDialog.datePicker.maxDate = maxDate



        datePickerDialog.show()
    }


    fun showBirthDatePicker(context: Activity, listener: DatePickerDialog.OnDateSetListener) {
        val pastDate: Calendar = Calendar.getInstance()
        val futureDate: Calendar = Calendar.getInstance()
        val currentDate: Calendar = Calendar.getInstance()

        // min date
        pastDate.set(Calendar.MILLISECOND, pastDate.getMinimum(Calendar.MILLISECOND))
        pastDate.add(Calendar.YEAR, -100)
        val minDate = pastDate.time.time

        //max date
        futureDate.set(Calendar.MILLISECOND, futureDate.getMaximum(Calendar.MILLISECOND))
        futureDate.add(Calendar.YEAR, -10)
        val maxDate = futureDate.time.time

        val datePickerDialog = DatePickerDialog(
            context,
            listener,
            currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = minDate
        datePickerDialog.datePicker.maxDate = maxDate
        datePickerDialog.show()
    }

}