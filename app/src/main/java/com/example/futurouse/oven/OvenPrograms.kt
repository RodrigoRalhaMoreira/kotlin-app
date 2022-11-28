package com.example.futurouse.oven

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
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


class OvenPrograms : Fragment(R.layout.fragment_oven_programs) {

    var settings = arrayListOf<MaterialCardView>()
    var schedules = arrayListOf<ImageView>()

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
                    (it is TextView) && (it.text.contains("º"))
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
        for (schedule in schedules) {
            schedule.setOnClickListener {
                val picker =
                    MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(12)
                        .setMinute(0)
                        .setTitleText("Set starting time")
                        .build()
                picker.show(childFragmentManager, "schedule")
            }
        }
    }
}

