package com.example.futurouse.oven

import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.futurouse.R
import kotlinx.android.synthetic.main.oven_activity.*

class OvenActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.oven_activity)

        val backButton = findViewById<ImageView>(R.id.backButton)

        backButton.setOnClickListener { this.finish() }

        val modeFragment = OvenMode()
        val tempFragment = OvenTemperature()
        val timerFragment = OvenTimer()
        val programsFragment = OvenPrograms()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.ovenFragContainer, modeFragment)
            commit()
        }

        modeButton.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.ovenFragContainer, modeFragment)
                commit()
            }
            setActiveTab(modeButton)
        }

        tempButton.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.ovenFragContainer, tempFragment)
                commit()
            }
            setActiveTab(modeButton)
        }

        timerButton.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.ovenFragContainer, timerFragment)
                commit()
            }
            setActiveTab(modeButton)
        }

        programsButton.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.ovenFragContainer, programsFragment)
                commit()
            }
            setActiveTab(modeButton)
        }

    }

    private fun setActiveTab(button: CardView) {
        //button.setBackgroundColor(Color.rgb())
    }


}