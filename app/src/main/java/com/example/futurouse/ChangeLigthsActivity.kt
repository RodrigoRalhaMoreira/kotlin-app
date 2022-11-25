package com.example.futurouse

import android.animation.ObjectAnimator
import android.os.Bundle
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import data.Lights
import data.Utils
import kotlinx.android.synthetic.main.activity_kitchen.*
import kotlinx.android.synthetic.main.lights_change_screen.*

class ChangeLigthsActivity  : AppCompatActivity(){

    var lightsOn = false;
    var lightPercentage = 0;
    var lightColor = 0; //0-yellow,1-red,2-blue
    var lightState : Lights? = null
    var  lights: List<Lights>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lights_change_screen)

        val extras = intent.extras;


        var roomName = findViewById<TextView>(R.id.textView3)

        if (extras != null) {
            roomName.text = extras.getString("roomName")
        }

        val jsonFileString = Utils.getJsonDataFromAsset(applicationContext, "LightsState.json")

        val gson = Gson()
        val lightType = object : TypeToken<List<Lights>>() {}.type
        lights = gson.fromJson(jsonFileString, lightType)

        lightState = lights?.get(0)

        lightsOn = lightState!!.lightsOn

        lightColor = lightState!!.lightColor

        var lightColorMenuOpen = false

        var seekbar = findViewById<SeekBar>(R.id.seekbar)
        val lightText = findViewById<TextView>(R.id.textView)
        val onOffBtn = findViewById<ImageView>(R.id.imageView1)
        val darkening = findViewById<CardView>(R.id.cardView)
        val yellowBtn = findViewById<CardView>(R.id.cardView2)
        val redBtn = findViewById<CardView>(R.id.cardView1)
        val blueBtn = findViewById<CardView>(R.id.cardView3)

        when (lightColor) {
            2 -> {
                yellowBtn.translationZ = 9F
                redBtn.translationZ = 9F
                blueBtn.translationZ = 11F
                seekbar.progressDrawable = getDrawable(R.drawable.blue_track)
            }
            1 ->{
                yellowBtn.translationZ = 9F
                redBtn.translationZ = 11F
                blueBtn.translationZ = 9F
                seekbar.progressDrawable = getDrawable(R.drawable.red_track)
            }
            else -> {
                yellowBtn.translationZ = 11F
                redBtn.translationZ = 9F
                blueBtn.translationZ = 9F
                seekbar.progressDrawable = getDrawable(R.drawable.yellow_track)
            }
        }

        if(lightsOn){
            onOffBtn.setImageResource(R.drawable.lights_on_symbol)
            darkening.alpha = 0.0f
        }else{
            onOffBtn.setImageResource(R.drawable.lights_off_symbol)
            darkening.alpha = 0.25f
        }

        lightPercentage = lightState!!.lightPercentage

        seekbar.progress = lightState!!.lightPercentage

        seekbar.alpha = 0.4f + (lightPercentage/100f)

        lightText.text = "$lightPercentage%"

        yellowBtn.setOnClickListener {
            if(lightColorMenuOpen){
                var progressMem = lightPercentage
                seekbar.progressDrawable = getDrawable(R.drawable.yellow_track)
                seekbar.progress = 0
                if (progressMem != null) {
                    seekbar.progress = progressMem
                }
                val objectAnimator = ObjectAnimator.ofFloat(yellowBtn,"x", 69.8f)
                val objectAnimator1 = ObjectAnimator.ofFloat(redBtn,"x", 69.8f)
                objectAnimator.duration = 1000
                objectAnimator1.duration = 1000
                objectAnimator.start()
                objectAnimator1.start()
                lightColorMenuOpen = false
                yellowBtn.translationZ = 11F
                redBtn.translationZ = 9F
                blueBtn.translationZ = 9F
                lightColor = 2

            }else{
                val objectAnimator = ObjectAnimator.ofFloat(yellowBtn,"x", 240f)
                val objectAnimator1 = ObjectAnimator.ofFloat(redBtn,"x", 410f)
                objectAnimator.duration = 1000
                objectAnimator1.duration = 1000
                objectAnimator.start()
                objectAnimator1.start()
                lightColorMenuOpen = true
            }
        }

        redBtn.setOnClickListener {
            if(lightColorMenuOpen == true){
                var progressMem = lightPercentage
                seekbar.progressDrawable = getDrawable(R.drawable.red_track)
                seekbar.progress = 0
                if (progressMem != null) {
                    seekbar.progress = progressMem
                }
                val objectAnimator = ObjectAnimator.ofFloat(yellowBtn,"x", 69.8f)
                val objectAnimator1 = ObjectAnimator.ofFloat(redBtn,"x", 69.8f)
                objectAnimator.duration = 1000
                objectAnimator1.duration = 1000
                objectAnimator.start()
                objectAnimator1.start()
                lightColorMenuOpen = false
                yellowBtn.translationZ = 9F
                redBtn.translationZ = 11F
                blueBtn.translationZ = 9F
                lightColor = 1

            }else{
                val objectAnimator = ObjectAnimator.ofFloat(yellowBtn,"x", 240f)
                val objectAnimator1 = ObjectAnimator.ofFloat(redBtn,"x", 410f)
                objectAnimator.duration = 1000
                objectAnimator1.duration = 1000
                objectAnimator.start()
                objectAnimator1.start()
                lightColorMenuOpen = true
            }
        }

        blueBtn.setOnClickListener {
            if(lightColorMenuOpen){
                var progressMem = lightPercentage
                seekbar.progressDrawable = getDrawable(R.drawable.blue_track)
                seekbar.progress = 0
                if (progressMem != null) {
                    seekbar.progress = progressMem
                }
                val objectAnimator = ObjectAnimator.ofFloat(yellowBtn,"x", 69.8f)
                val objectAnimator1 = ObjectAnimator.ofFloat(redBtn,"x", 69.8f)
                objectAnimator.duration = 1000
                objectAnimator1.duration = 1000
                objectAnimator.start()
                objectAnimator1.start()
                lightColorMenuOpen = false
                yellowBtn.translationZ = 9F
                redBtn.translationZ = 9F
                blueBtn.translationZ = 11F
                lightColor = 0

            }else{
                val objectAnimator = ObjectAnimator.ofFloat(yellowBtn,"x", 240f)
                val objectAnimator1 = ObjectAnimator.ofFloat(redBtn,"x", 410f)
                objectAnimator.duration = 1000
                objectAnimator1.duration = 1000
                objectAnimator.start()
                objectAnimator1.start()
                lightColorMenuOpen = true
            }
        }

        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if(p2)lightPercentage = p1
                lightText.text = "$p1%"
                seekbar.alpha = 0.4f + (p1/100f)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

        onOffBtn.setOnClickListener {
            lightsOn = !lightsOn!!
            if(lightsOn){
                onOffBtn.setImageResource(R.drawable.lights_on_symbol)
                darkening.alpha = 0.0f
                seekbar.isEnabled = true
                seekbar.progress = lightPercentage!!
                blueBtn.isClickable = true
                yellowBtn.isClickable = true
                redBtn.isClickable = true
            }else{
                onOffBtn.setImageResource(R.drawable.lights_off_symbol)
                darkening.alpha = 0.25f
                seekbar.progress = 0
                seekbar.isEnabled = false
                blueBtn.isClickable = false
                yellowBtn.isClickable = false
                redBtn.isClickable = false
            }
        }

        imageView.setOnClickListener { this.finish() }
    }

    override fun onStop() {
        super.onStop()
        val gson = Gson()
        val gsonPretty = GsonBuilder().setPrettyPrinting().create()

    }
}