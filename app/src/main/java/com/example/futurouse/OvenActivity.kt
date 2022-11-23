package com.example.futurouse

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import kotlinx.android.synthetic.main.fragment_oven_mode.*
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
        }

        tempButton.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.ovenFragContainer, tempFragment)
                commit()
            }
        }

        timerButton.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.ovenFragContainer, timerFragment)
                commit()
            }
        }

        programsButton.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.ovenFragContainer, programsFragment)
                commit()
            }
        }

    }


}