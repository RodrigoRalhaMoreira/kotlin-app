package com.example.futurouse

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import me.tankery.lib.circularseekbar.CircularSeekBar


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var currTemp = 10f
    var newTemp = 10f

    val rotateValue = 2800L
    val rotateTotalModes = 3L
    var rotateMode = 0L
    var rotateStep = 920

    var blindsPerc = arrayOf("0%", "25%", "50%", "75%", "100%")
    var currentBlindsIndex = 0
    var lightPercentage = 20
    var lightsOn = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // more info question mark
        val button: AppCompatImageButton = findViewById(R.id.button)

        val balloon = Balloon.Builder(applicationContext)
            .setWidthRatio(1.0f)
            .setHeight(BalloonSizeSpec.WRAP)
            .setText("This is a simple ballonn message")
            .setTextColorResource(R.color.white)
            .setTextSize(15f)
            .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
            .setArrowSize(10)
            .setArrowPosition(0.5f)
            .setPadding(12)
            .setCornerRadius(8f)
            .setBackgroundColorResource(R.color.purple_200)
            .setBalloonAnimation(BalloonAnimation.ELASTIC)
            .build()

        button.setOnClickListener(View.OnClickListener() {
            balloon.showAlignBottom(button)
        })

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        toggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(toggle)
        toggle.drawerArrowDrawable.color = R.color.black
        toggle.syncState()

        nav_menu.setNavigationItemSelectedListener(this)

        supportActionBar?.title = ""
        changeFragment(Home())

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)
        when(item.itemId) {
            R.id.home -> {
                changeFragment(Home())
            }
            R.id.rooms -> {
                changeFragment(Rooms())
            }
            R.id.about -> {
                changeFragment(About())
            }
            R.id.guide -> {
                changeFragment(Guide())
            }
            R.id.settings -> {
                changeFragment(Settings())
            }
        }
        return true
    }

    fun changeFragment(frag: Fragment) {
        val fragment = supportFragmentManager.beginTransaction()
        fragment.replace(R.id.fragment_container, frag).commit()
    }

    fun blinds(view: View) {
        setContentView(R.layout.blinds_screen)
    }

    fun blindsUp(view: View) {
        val blindPercentage = findViewById<TextView>(R.id.blind_percentage)
        if (currentBlindsIndex != 4) {
            currentBlindsIndex += 1
            blindPercentage.text = blindsPerc[currentBlindsIndex]
            changeBlindsImage()
        }
    }

    fun blindsDown(view: View) {
        val blindPercentage = findViewById<TextView>(R.id.blind_percentage)
        if (currentBlindsIndex != 0) {
            currentBlindsIndex -= 1
            blindPercentage.text = blindsPerc[currentBlindsIndex]
            changeBlindsImage()
        }
    }

    fun changeBlindsImage() {
        val blindImage = findViewById<ImageView>(R.id.imageView3)

        when(currentBlindsIndex) {
            0 -> blindImage.setImageResource(R.drawable.blinds_0)
            1 -> blindImage.setImageResource(R.drawable.blinds_30)
            2 -> blindImage.setImageResource(R.drawable.blinds_50)
            3 -> blindImage.setImageResource(R.drawable.blinds_70)
            4 -> blindImage.setImageResource(R.drawable.blinds_100)
        }
    }

    fun thermostat(view: View){

        setContentView(R.layout.thermostat_screen)

        val fanImg = findViewById<ImageView>(R.id.imageView2);


        val rotate = AnimationUtils.loadAnimation(this, R.anim.rotate)
        rotate.duration = rotateValue
        rotate.setInterpolator(LinearInterpolator())
        fanImg.animation = rotate

        val currentTemperature = findViewById<TextView>(R.id.textView2)
        val newTemperature = findViewById<TextView>(R.id.textView3)
        val thermometer = findViewById<ImageView>(R.id.imageView1)
        currentTemperature.text = "${currTemp.toInt()} ºC"
        newTemperature.text = "${newTemp.toInt()} ºC"

        var seekbar: CircularSeekBar? = findViewById(R.id.seekbar)
        seekbar?.progress = currTemp
        seekbar?.circleProgressColor = Color.rgb(currTemp.toInt()*(255/35),0,255-currTemp.toInt()*(255/35))
        seekbar?.setOnSeekBarChangeListener(object : CircularSeekBar.OnCircularSeekBarChangeListener{
            override fun onProgressChanged(
                circularSeekBar: CircularSeekBar?,
                progress: Float,
                fromUser: Boolean
            ) {
                seekbar.circleProgressColor = Color.rgb(progress.toInt()*(255/35),0,255-progress.toInt()*(255/35))
                newTemp = progress
                newTemperature.text = "${newTemp.toInt()} ºC"
            }

            override fun onStopTrackingTouch(seekBar: CircularSeekBar?) {
                val dif = newTemp - currTemp
                if(dif.toInt() != 0){
                    if(dif > 0.0f) thermometer.setImageResource(R.drawable.thermometer_temperature_up)
                    else thermometer.setImageResource(R.drawable.thermometer_temperature_down)
                    thermometer.alpha = 255F
                    startCounter(currentTemperature,thermometer)
                }
            }

            override fun onStartTrackingTouch(seekBar: CircularSeekBar?) {

            }
        })

        fanImg.setOnClickListener {
            rotate.duration = rotateValue - rotateStep*(rotateMode % rotateTotalModes)
            rotateMode++
            fanImg.animation = rotate
        }
    }
    fun startCounter(textView : TextView,imageView: ImageView){
        val timeStart = 5000L
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

    fun lightsChange(view:View){
        setContentView(R.layout.lights_change_screen)
        var seekbar = findViewById<SeekBar>(R.id.seekbar)
        val lightText = findViewById<TextView>(R.id.textView)
        val onOffBtn = findViewById<ImageView>(R.id.imageView1)
        val darkening = findViewById<CardView>(R.id.cardView)
        val yellowBtn = findViewById<CardView>(R.id.cardView2)
        val redBtn = findViewById<CardView>(R.id.cardView1)
        val blueBtn = findViewById<CardView>(R.id.cardView3)

        if(lightsOn){
            onOffBtn.setImageResource(R.drawable.lights_on_symbol)
            darkening.alpha = 0.0f
        }else{
            onOffBtn.setImageResource(R.drawable.lights_off_symbol)
            darkening.alpha = 0.25f
        }

        seekbar.alpha = 0.4f + (lightPercentage/100f)

        lightText.text = "$lightPercentage%"

        yellowBtn.setOnClickListener {
            if(lightColorMenuOpen){
                var progressMem = lightPercentage
                seekbar.progressDrawable = getDrawable(R.drawable.yellow_track)
                seekbar.progress = 0
                seekbar.progress = progressMem
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
            if(lightColorMenuOpen){
                var progressMem = lightPercentage
                seekbar.progressDrawable = getDrawable(R.drawable.red_track)
                seekbar.progress = 0
                seekbar.progress = progressMem
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
                seekbar.progress = progressMem
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
            lightsOn = !lightsOn
            if(lightsOn){
                onOffBtn.setImageResource(R.drawable.lights_on_symbol)
                darkening.alpha = 0.0f
                seekbar.isEnabled = true
                seekbar.progress = lightPercentage
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
    }
}
