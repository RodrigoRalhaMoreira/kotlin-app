package com.example.futurouse.oven

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.get
import androidx.core.view.iterator
import com.example.futurouse.MainActivity
import com.example.futurouse.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.oven_activity.*
import java.util.concurrent.TimeUnit
import kotlin.math.absoluteValue

class OvenActivity : AppCompatActivity()  {

    private lateinit var currentActiveTab: CardView

    private var fragContainer = R.drawable.layout_border

    private var minutes: Int = -1
    private var hours: Int = -1

    private lateinit var timer: CountDownTimer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.oven_activity)

        currentActiveTab = modeButton
        currentActiveTab.setCardBackgroundColor(Color.parseColor("#ff9e6f"))

        backButton.setOnClickListener { this.finish() }

        val modeFragment = OvenMode()
        val tempFragment = OvenTemperature()
        val timerFragment = OvenTimer()
        val programsFragment = OvenPrograms()

        supportFragmentManager.beginTransaction().apply {
            ovenFragContainer.setBackgroundResource(fragContainer)
            replace(R.id.ovenFragContainer, modeFragment)
            commit()
        }

        modeButton.setOnClickListener {
            ovenFragContainer.setBackgroundResource(fragContainer)
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.ovenFragContainer, modeFragment)
                commit()
            }
            setActiveTab(modeButton)
        }

        tempButton.setOnClickListener {
            ovenFragContainer.setBackgroundResource(fragContainer)
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.ovenFragContainer, tempFragment)
                commit()
            }
            setActiveTab(tempButton)
        }

        timerButton.setOnClickListener {
            ovenFragContainer.setBackgroundResource(fragContainer)
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.ovenFragContainer, timerFragment)
                commit()
            }
            setActiveTab(timerButton)
        }

        programsButton.setOnClickListener {
            ovenFragContainer.setBackgroundResource(R.drawable.frag_programs_container)
            supportFragmentManager.beginTransaction().apply {
                add(R.id.ovenFragContainer, programsFragment)
                commit()
            }
            setActiveTab(programsButton)
        }

        flames.scale = 10f

        onOffButton.setOnClickListener {
            if (onOffButton.text.contains("ON")) {
                onOffButton.text = "TURN OFF"

                startTimer()
            }
            else {
                onOffButton.text = "TURN ON"

                timer.cancel()

                timerIcon.visibility = View.VISIBLE
                countdown.visibility = View.INVISIBLE

                flames.visibility = View.INVISIBLE
            }
        }

        bottom_navigation.menu[3].isChecked = true

        bottom_navigation.menu[0].setOnMenuItemClickListener {
            if (!it.isChecked) {
                var intent = Intent(this, MainActivity::class.java)
                intent.putExtra("FLAG_NAV", 0)
                startActivity(intent)
            }
            true
        }
        bottom_navigation.menu[1].setOnMenuItemClickListener {
            if (!it.isChecked) {
                var intent = Intent(this, MainActivity::class.java)
                intent.putExtra("FLAG_NAV", 1)
                startActivity(intent)
            }
            true
        }
        bottom_navigation.menu[2].setOnMenuItemClickListener {
            if (!it.isChecked) {
                var intent = Intent(this, MainActivity::class.java)
                intent.putExtra("FLAG_NAV", 2)
                startActivity(intent)
            }
            true
        }

    }

    private fun setActiveTab(button: CardView) {
        currentActiveTab.setCardBackgroundColor(Color.parseColor("#FFFAC7"))
        button.setCardBackgroundColor(Color.parseColor("#ff9e6f"))
        currentActiveTab = button
    }

    fun setMinutes(minutes: Int) {
        this.minutes = minutes
    }

    fun setHours(hours: Int) {
        this.hours = hours
    }

    private fun startTimer() {
        var hoursInMilli = 0L
        var minutesInMilli = 0L

        if (hours > 0) hoursInMilli = TimeUnit.HOURS.toMillis(hours.toLong())
        if (minutes > 0) minutesInMilli = TimeUnit.MINUTES.toMillis(minutes.toLong())

        var duration = hoursInMilli + minutesInMilli

        countdown.visibility = View.VISIBLE
        timerIcon.visibility = View.INVISIBLE

        fadeInFlames()

        timer = object: CountDownTimer(duration, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                var hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished).absoluteValue
                var minutes = ( TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60 ).absoluteValue
                var seconds = ( TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60 ).absoluteValue

                if (hours > 0) countdown.text = "$hours : $minutes : $seconds"
                else countdown.text = "$minutes : $seconds"

            }

            override fun onFinish() {
                timerIcon.visibility = View.VISIBLE
                countdown.visibility = View.INVISIBLE

                flames.visibility = View.INVISIBLE
                onOffButton.text = "TURN ON"
            }

        }.start()
    }

    private fun fadeInFlames() {
        val fadeIn = AlphaAnimation(0f, 1f)
        fadeIn.duration = 1000

        val animation = AnimationSet(false)
        animation.addAnimation(fadeIn)

        flames.animation = animation

        flames.visibility = View.VISIBLE
    }


}