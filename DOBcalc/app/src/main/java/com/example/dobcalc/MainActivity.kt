package com.example.dobcalc

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var tvSelectedDate : TextView? = null
    private var tvAgeInMinutes : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSelectDate:Button = findViewById(R.id.btnSelectDate)
        tvSelectedDate = findViewById(R.id.dateVariable)
        tvAgeInMinutes = findViewById(R.id.resultVariable)

        btnSelectDate.setOnClickListener {
            clickDatePicker()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun clickDatePicker(){
        val myCalendar = Calendar.getInstance()
        var year = myCalendar.get(Calendar.YEAR)
        var month = myCalendar.get(Calendar.MONTH)
        var day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{
                _, year, month, dayOfMonth ->

            val selectedDate = "$dayOfMonth/${month+1}/$year"

            tvSelectedDate?.text = selectedDate

            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.GERMAN)

            val theDate = sdf.parse(selectedDate)
            theDate?.let {
                val selectedDateInMinutes = theDate.time / 60000

                val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                currentDate?.let {
                    val currentDateInMinutes = currentDate.time / 60000

                    val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes

                    when {
                        differenceInMinutes>=52596000 -> {
                            tvAgeInMinutes?.text = "${differenceInMinutes.toString()}, Boomer!"
                        }
                        differenceInMinutes>=0 -> {
                            tvAgeInMinutes?.text = differenceInMinutes.toString()
                        }
                        else -> {
                            tvAgeInMinutes?.text = "Error! Else method that shouldn't be called was called! Contact support!"
                        }
                    }
                }
            }
        }, year, month, day)

        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()
    }

}