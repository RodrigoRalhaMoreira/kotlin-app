package com.example.futurouse.oven

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import com.example.futurouse.R
import kotlinx.android.synthetic.main.fragment_oven_temperature.*
import me.tankery.lib.circularseekbar.CircularSeekBar

class OvenTemperature : Fragment(R.layout.fragment_oven_temperature) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        tempSeekBar.setOnSeekBarChangeListener(object : CircularSeekBar.OnCircularSeekBarChangeListener{
            override fun onProgressChanged(
                circularSeekBar: CircularSeekBar?,
                progress: Float,
                fromUser: Boolean
            ) {
                temperature.text = "${progress.toInt()}º"
            }

            override fun onStopTrackingTouch(seekBar: CircularSeekBar?) {
                val ovenTempView = activity?.findViewById<TextView>(R.id.ovenTemp)
                ovenTempView?.text = temperature.text
            }

            override fun onStartTrackingTouch(seekBar: CircularSeekBar?) {}
        })
    }
}