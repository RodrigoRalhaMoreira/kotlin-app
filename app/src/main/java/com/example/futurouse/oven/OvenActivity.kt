package com.example.futurouse.oven

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.futurouse.R
import kotlinx.android.synthetic.main.oven_activity.*

class OvenActivity : AppCompatActivity()  {

    private lateinit var currentActiveTab: CardView

    private var fragContainer = R.drawable.layout_border

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
            ovenFragContainer.setBackgroundResource(0)
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.ovenFragContainer, programsFragment)
                commit()
            }
            setActiveTab(programsButton)
        }

        flames.scale = 10f

        onOffButton.setOnClickListener {
            if (onOffButton.text.contains("ON")) {
                onOffButton.text = "TURN OFF"

                val fadeIn = AlphaAnimation(0f,1f)
                fadeIn.duration = 1000

                val animation = AnimationSet(false)
                animation.addAnimation(fadeIn)

                flames.animation = animation

                flames.visibility = View.VISIBLE

            }
            else {
                onOffButton.text = "TURN ON"

                val fadeOut = AlphaAnimation(1f,0f)
                fadeOut.duration = 500

                val animation = AnimationSet(false)
                animation.addAnimation(fadeOut)

                flames.animation = animation

                flames.visibility = View.INVISIBLE

            }
        }




    }

    private fun setActiveTab(button: CardView) {
        currentActiveTab.setCardBackgroundColor(Color.parseColor("#FFFAC7"))
        button.setCardBackgroundColor(Color.parseColor("#ff9e6f"))
        currentActiveTab = button
    }


}