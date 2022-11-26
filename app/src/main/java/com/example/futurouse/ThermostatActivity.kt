package com.example.futurouse

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import data.Lights
import data.Thermostat
import data.Utils
import kotlinx.android.synthetic.main.lights_change_screen.*
import kotlinx.android.synthetic.main.lights_change_screen.imageView
import kotlinx.android.synthetic.main.thermostat_screen.*
import me.tankery.lib.circularseekbar.CircularSeekBar

class ThermostatActivity : AppCompatActivity() {

    var currTemp = 10f
    var newTemp = 10f

    val rotateBaseValue = 1F
    val rotateTotalModes = 3f
    var rotateMode = 0f
    var rotateStep = 1.5f
    var isOn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.thermostat_screen)

        val fanImg = findViewById<LottieAnimationView>(R.id.imageView2);


        val extras = intent.extras;


        var roomName = findViewById<TextView>(R.id.textView10)

        if (extras != null) {
            roomName.text = extras.getString("roomName")
        }

        val jsonFileString = Utils.getJsonDataFromAsset(applicationContext, "ThermostatState.json")

        val gson = Gson()
        val thermostatType = object : TypeToken<List<Thermostat>>() {}.type
        var thermostats : List<Thermostat> = gson.fromJson(jsonFileString, thermostatType)

        var thermostatState = thermostats?.get(0)

        currTemp = thermostatState?.currTemp!!
        newTemp = currTemp
        rotateMode = thermostatState.rotateMode
        isOn = thermostatState.isOn



        if(isOn){
            onOff.setImageResource(R.drawable.on_symbol)
            fanImg.speed = rotateBaseValue + rotateStep*(rotateMode % rotateTotalModes)
        }
        else{
            onOff.setImageResource(R.drawable.off_symbol)
            fanImg.speed = 0f
        }

        val currentTemperature = findViewById<TextView>(R.id.textView2)
        val newTemperature = findViewById<TextView>(R.id.textView3)
        val thermometer = findViewById<ImageView>(R.id.imageView1)
        currentTemperature.text = "${currTemp.toInt()} ºC"
        newTemperature.text = "${newTemp.toInt()} ºC"

        var seekbar: CircularSeekBar? = findViewById(R.id.seekbar)
        seekbar?.progress = currTemp
        if(isOn){
            seekbar?.isEnabled = true
            seekbar?.circleProgressColor = Color.rgb(newTemp.toInt()*(255/35),0,255-newTemp.toInt()*(255/35))
        }else{

            seekbar?.circleProgressColor = Color.rgb(128,128,128)
            seekbar?.isEnabled = false
        }
        seekbar?.setOnSeekBarChangeListener(object : CircularSeekBar.OnCircularSeekBarChangeListener{
            override fun onProgressChanged(
                circularSeekBar: CircularSeekBar?,
                progress: Float,
                fromUser: Boolean
            ) {
                if(isOn){
                    seekbar?.circleProgressColor = Color.rgb(newTemp.toInt()*(255/35),0,255-newTemp.toInt()*(255/35))
                newTemp = progress
                newTemperature.text = "${newTemp.toInt()} ºC"
                }
            }

            override fun onStopTrackingTouch(seekBar: CircularSeekBar?) {
                val dif = newTemp - currTemp
                if(dif.toInt() != 0 && isOn){
                    if(dif > 0.0f) thermometer.setImageResource(R.drawable.thermometer_temperature_up)
                    else thermometer.setImageResource(R.drawable.thermometer_temperature_down)
                    thermometer.alpha = 1f
                    startCounter(currentTemperature,thermometer)
                }
            }

            override fun onStartTrackingTouch(seekBar: CircularSeekBar?) {

            }
        })

        fanImg.setOnClickListener {
            if(isOn){
                rotateMode++
                fanImg.speed = rotateBaseValue + rotateStep*(rotateMode % rotateTotalModes)

                when (rotateMode % rotateTotalModes) {
                    0f -> {
                        textViewFanSpeed.text = "Slow"
                    }
                    1f -> {
                        textViewFanSpeed.text = "Normal"
                    }
                    2f -> {
                        textViewFanSpeed.text = "Fast"
                    }
                }
            }
        }

        onOff.setOnClickListener{
            isOn = !isOn
            if(isOn){
                when (rotateMode % rotateTotalModes) {
                    0f -> {
                        textViewFanSpeed.text = "Slow"
                    }
                    1f -> {
                        textViewFanSpeed.text = "Normal"
                    }
                    2f -> {
                        textViewFanSpeed.text = "Fast"
                    }
                }
                textViewOn.text = "On"
                onOff.setImageResource(R.drawable.on_symbol)
                seekbar?.circleProgressColor = Color.rgb(newTemp.toInt()*(255/35),0,255-newTemp.toInt()*(255/35))
                seekbar?.isEnabled = true
                fanImg.speed = rotateBaseValue + rotateStep*(rotateMode % rotateTotalModes)
            }else{
                textViewFanSpeed.text = "Idle"
                textViewOn.text = "Off"
                onOff.setImageResource(R.drawable.off_symbol)
                seekbar?.isEnabled = false
                seekbar?.circleProgressColor = Color.rgb(128,128,128)
                fanImg.speed = 0f
            }
        }


        imageView.setOnClickListener { this.finish() }
    }

    fun startCounter(textView : TextView,imageView: ImageView){
        val timeStart = 3000L
        val timeStep = 1000L
        object : CountDownTimer(timeStart, timeStep) {
            val dif = newTemp - currTemp
            val step = dif/(timeStart/1000)
            override fun onTick(l: Long) {
                currTemp += step
                textView.text = "${currTemp.toInt()} ºC"
            }
            override fun onFinish() {
                imageView.alpha = 0F
            }
        }.start()
    }
}