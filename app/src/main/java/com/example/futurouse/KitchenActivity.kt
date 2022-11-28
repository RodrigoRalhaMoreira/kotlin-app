package com.example.futurouse

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ContentFrameLayout
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.drawerlayout.widget.DrawerLayout
import com.example.futurouse.oven.OvenActivity
import kotlinx.android.synthetic.main.activity_kitchen.*
import kotlinx.android.synthetic.main.activity_kitchen.night_btn
import kotlinx.android.synthetic.main.activity_kitchen.party_btn
import kotlinx.android.synthetic.main.activity_kitchen.relax_btn
import kotlinx.android.synthetic.main.activity_kitchen.textView3
import kotlinx.android.synthetic.main.activity_kitchen.wake_btn
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*

class KitchenActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_kitchen)

        ovenButton.setOnClickListener {
            Intent(this, OvenActivity::class.java).also {
                startActivity(it)
            }
        }

        lightButton.setOnClickListener{
            Intent(this, ChangeLigthsActivity::class.java).also {
                it.putExtra("roomName","Kitchen")
                startActivity(it)
            }
        }

        thermostatButton.setOnClickListener{
            Intent(this, ThermostatActivity::class.java).also {
                it.putExtra("roomName","Kitchen")
                startActivity(it)
            }
        }

        thermostatButton.setOnClickListener{
            Intent(this, ThermostatActivity::class.java).also {
                it.putExtra("roomName","Kitchen")
                startActivity(it)
            }
        }

        backButton.setOnClickListener { this.finish() }
        wake_btn?.setCardBackgroundColor(Color.parseColor("#DCDCD0"))
        night_btn.setOnClickListener( View.OnClickListener() {
            setDefaultButtonsBackground()
            setSelectedViewCardBackground(night_btn)
            kitchen?.setBackgroundResource(R.drawable.night_background)
            setDarkMode(this.findViewById(android.R.id.content))
        })
        wake_btn.setOnClickListener( View.OnClickListener() {
            setDefaultButtonsBackground()
            setSelectedViewCardBackground(wake_btn)
            kitchen?.setBackgroundResource(R.drawable.morning)
            setLightMode(this.findViewById(android.R.id.content))
        })
        party_btn.setOnClickListener( View.OnClickListener() {
            setDefaultButtonsBackground()
            setSelectedViewCardBackground(party_btn)
            kitchen?.setBackgroundResource(R.drawable.party_final_bg)
            setDarkMode(this.findViewById(android.R.id.content))
        })
        relax_btn.setOnClickListener( View.OnClickListener() {
            setDefaultButtonsBackground()
            setSelectedViewCardBackground(relax_btn)
            kitchen?.setBackgroundResource(R.drawable.relax_background)
            setDarkMode(this.findViewById(android.R.id.content))
        })


    }

    fun setSelectedViewCardBackground(cardView: CardView) {
        cardView?.setCardBackgroundColor(Color.parseColor("#DCDCD0"))
    }


    fun setDefaultButtonsBackground() {
        wake_btn?.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
        night_btn?.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
        party_btn?.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
        relax_btn?.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
    }

    fun setDarkMode(view: View) {
        textView6?.setTextColor(Color.parseColor("#FFFFFF"))
        textView3?.setTextColor(Color.parseColor("#FFFFFF"))
        temp?.setTextColor(Color.parseColor("#FFFFFF"))
        card_text2?.setTextColor(Color.parseColor("#FFFFFF"))
        card_text3?.setTextColor(Color.parseColor("#FFFFFF"))
        kitchenLightStatusText?.setTextColor(Color.parseColor("#FFFFFF"))
        kitchenTitle?.setTextColor(Color.parseColor("#FFFFFF"))
        profileTextView?.setTextColor(Color.parseColor("#FFFFFF"))
        textAccessoriesView?.setTextColor(Color.parseColor("#FFFFFF"))
        backButton?.setImageResource(R.drawable.back_arrow_white)
    }

    fun setLightMode(view: View) {
        textView6?.setTextColor(Color.parseColor("#000000"))
        textView3?.setTextColor(Color.parseColor("#000000"))
        temp?.setTextColor(Color.parseColor("#000000"))
        card_text2?.setTextColor(Color.parseColor("#000000"))
        card_text3?.setTextColor(Color.parseColor("#000000"))
        kitchenLightStatusText?.setTextColor(Color.parseColor("#000000"))
        kitchenTitle?.setTextColor(Color.parseColor("#000000"))
        profileTextView?.setTextColor(Color.parseColor("#000000"))
        textAccessoriesView?.setTextColor(Color.parseColor("#000000"))
        backButton?.setImageResource(R.drawable.back_arrow_black)
    }
}