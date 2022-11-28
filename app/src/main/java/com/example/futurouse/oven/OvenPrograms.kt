package com.example.futurouse.oven

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.example.futurouse.R
import com.google.android.material.card.MaterialCardView
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.android.synthetic.main.fragment_oven_programs.*
import java.util.*


class OvenPrograms : Fragment(R.layout.fragment_oven_programs) {

    private var settings = arrayListOf<MaterialCardView>()
    private var schedules = arrayListOf<ImageView>()

    private var savedHours = arrayOfNulls<Int>(4)
    private var savedMinutes = arrayOfNulls<Int>(4)

    var checkedSetting: MaterialCardView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settings.addAll(arrayListOf(setting1, setting2, setting3, setting4))
        schedules.addAll(arrayListOf(scheduleOven1, scheduleOven2, scheduleOven3, scheduleOven4))

        this.setCheckedListeners()
        this.setScheduleListeners()

    }

    private fun setCheckedListeners() {
        for (setting in settings) {
            setting.setOnLongClickListener {
                if (checkedSetting != null) {
                    checkedSetting!!.isChecked = false
                }
                setting.isChecked = !setting.isChecked
                checkedSetting = setting

                var ovenMode = ( ( setting.children.first() as ConstraintLayout ).children.find {
                    it is ImageView
                } ) as ImageView

                var ovenTemp = ( ( setting.children.first() as ConstraintLayout ).children.find {
                    (it is TextView) && (it.text.contains("ยบ"))
                } ) as TextView

                activity?.findViewById<ImageView>(R.id.currentMode)?.setImageDrawable(ovenMode.drawable)
                activity?.findViewById<TextView>(R.id.ovenTemp)?.text = ovenTemp.text

                var bundle = Bundle()
                bundle.putString("temp", ovenTemp.text as String)

                val tempFragment = OvenTemperature()
                tempFragment.arguments = bundle

                (activity as OvenActivity).setNewTempFrag(tempFragment)

                true
            }
        }
    }

    private fun setScheduleListeners() {

        schedules.forEachIndexed { index, schedule ->
            schedule.setOnClickListener {
                var hour = 12
                var minute = 0
                if (savedHours[index] != null)  hour = savedHours[index]!!
                if (savedMinutes[index] != null)  minute = savedMinutes[index]!!
                val picker =
                    MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(hour)
                        .setMinute(minute)
                        .setTitleText("Set starting time")
                        .build()
                picker.show(childFragmentManager, "schedule")

                picker.addOnPositiveButtonClickListener {
                    savedHours[index] = picker.hour
                    savedMinutes[index] = picker.minute
                }
            }
        }
    }
}