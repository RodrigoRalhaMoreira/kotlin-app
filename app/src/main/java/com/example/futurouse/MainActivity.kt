package com.example.futurouse

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
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
import java.lang.Math.abs

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var currTemp = 10f
    var newTemp = 10f

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

    fun thermostat(view: View){

        setContentView(R.layout.thermostat_screen)


        val fanImg = findViewById<ImageView>(R.id.imageView2);
        val rotate = AnimationUtils.loadAnimation(this, R.anim.rotate)
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
}
