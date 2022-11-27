package com.example.futurouse.oven

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.NumberPicker
import com.example.futurouse.R

class OvenTimer : Fragment(R.layout.fragment_oven_timer) {

    val MINUTES_SYMBOL = "m"
    val HOURS_SYMBOL = "h"

    val MAX_MINUTES = 59
    val MAX_HOURS = 15

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var minutesPicker = view.findViewById<NumberPicker>(R.id.minutesPicker)
        var hoursPicker = view.findViewById<NumberPicker>(R.id.hoursPicker)

        minutesPicker.minValue = 0
        minutesPicker.maxValue = MAX_MINUTES

        hoursPicker.minValue = 0
        hoursPicker.maxValue = MAX_HOURS

        minutesPicker.displayedValues = createArray(MINUTES_SYMBOL, MAX_MINUTES+1)
        hoursPicker.displayedValues = createArray(HOURS_SYMBOL, MAX_HOURS+1)


        minutesPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            (activity as OvenActivity).setMinutes(newVal)
        }

        hoursPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            (activity as OvenActivity).setHours(newVal)
        }

    }

    private fun createArray(symbol: String, max: Int): Array<String?> {
        var tmpArr = arrayOfNulls<String>(max)

        for(i in 0 until max) {
            tmpArr[i] = "$i $symbol"
        }

        return tmpArr
    }
}