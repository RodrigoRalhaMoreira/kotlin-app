package com.example.futurouse

import android.app.AlertDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import kotlinx.android.synthetic.main.irrigation.*
import java.util.*

class IrrigationActivity : AppCompatActivity() {

    var startHour = 15
    var startMinute = 0
    var endHour = 17
    var endMinute = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.irrigation)

        goBackButton.setOnClickListener {
            this.finish()
        }

        startHour = 15
        startMinute = 0
        endHour = 17
        endMinute = 0
        val text = findViewById<TextView>(R.id.textView)
        text.text = String.format(Locale.getDefault(),"%02d:%02d",15,0)
        val text1 = findViewById<TextView>(R.id.textView1)
        text1.text = String.format(Locale.getDefault(),"%02d:%02d",17,0)
        val rightNow = Calendar.getInstance()
        val hour: Int =rightNow.get(Calendar.HOUR_OF_DAY)
        val minute: Int =rightNow.get(Calendar.MINUTE)
        val wateringCan = findViewById<LottieAnimationView>(R.id.WateringCan)

        if(hour>startHour && hour<endHour || hour>startHour && hour==endHour && minute<endMinute || hour==startHour && minute>startMinute && hour<endHour ) {
            wateringCan.alpha = 1f
        }
        else wateringCan.alpha = 0f
    }

    fun timepick(view: View){
        val rightNow = Calendar.getInstance()
        val hour: Int =rightNow.get(Calendar.HOUR_OF_DAY)
        val minute: Int =rightNow.get(Calendar.MINUTE)
        val wateringCan = findViewById<LottieAnimationView>(R.id.WateringCan)
        val onTimeSetListener =
            TimePickerDialog.OnTimeSetListener { timePicker, i, i1 ->
                if (i < endHour || (i == endHour && i1 < endMinute)) {
                    startHour = i
                    startMinute = i1
                    val text = findViewById<TextView>(R.id.textView)
                    text.text =
                        String.format(Locale.getDefault(), "%02d:%02d", startHour, startMinute)
                    if (hour > startHour && hour < endHour || hour > startHour && hour == endHour && minute < endMinute || hour == startHour && minute > startMinute && hour < endHour) {
                        wateringCan.alpha = 1f
                    } else wateringCan.alpha = 0f
                } else {
                    val toast: Toast = Toast.makeText(
                        this,
                        "Start time cant be greater than end time",
                        Toast.LENGTH_SHORT
                    )
                    toast.show()
                }
            }


        var timePickerDialog = TimePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT,onTimeSetListener,startHour,startMinute,true)
        timePickerDialog.setTitle("Select start time")
        timePickerDialog.show()
    }

    fun timepick1(view: View){
        val rightNow = Calendar.getInstance()
        val hour: Int =rightNow.get(Calendar.HOUR_OF_DAY)
        val minute: Int =rightNow.get(Calendar.MINUTE)
        val wateringCan = findViewById<LottieAnimationView>(R.id.WateringCan)
        val onTimeSetListener =
            TimePickerDialog.OnTimeSetListener { timePicker, i, i1 ->
                if (i > startHour || (i == startHour && i1 > startHour)) {
                    endHour = i
                    endMinute = i1
                    val text = findViewById<TextView>(R.id.textView1)
                    text.text = String.format(Locale.getDefault(), "%02d:%02d", endHour, endMinute)
                    if (hour > startHour && hour < endHour || hour > startHour && hour == endHour && minute < endMinute || hour == startHour && minute > startMinute && hour < endHour) {
                        wateringCan.alpha = 1f
                    } else wateringCan.alpha = 0f
                } else {
                    val toast: Toast = Toast.makeText(
                        this,
                        "Start time cant be lower than end time",
                        Toast.LENGTH_SHORT
                    )
                    toast.show()
                }
            }

        var timePickerDialog = TimePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT,onTimeSetListener,endHour,endMinute,true)
        timePickerDialog.setTitle("Select end time")
        timePickerDialog.show()
    }
}