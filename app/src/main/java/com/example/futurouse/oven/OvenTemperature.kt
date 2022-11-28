package com.example.futurouse.oven

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.setFragmentResultListener
import com.example.futurouse.R
import kotlinx.android.synthetic.main.fragment_oven_temperature.*
import me.tankery.lib.circularseekbar.CircularSeekBar

class OvenTemperature : Fragment(R.layout.fragment_oven_temperature) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        var bundle = this.arguments
        if (bundle != null && !bundle.isEmpty) {
            var newTemp = bundle?.getString("temp") as String
            temperature.text = newTemp
            tempSeekBar.progress = ( newTemp.filterNot { it == 'º' } ).toFloat()
        }




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