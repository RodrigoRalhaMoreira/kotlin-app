package com.example.futurouse
import android.content.Intent
import android.graphics.Color
import android.media.Image
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.futurouse.oven.OvenActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*


class Home : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        kitchenOven.setOnClickListener {
            var intent = Intent(activity,OvenActivity::class.java)
            startActivity(intent)
        }

        kitchenLight.setOnClickListener {
            var intent = Intent(activity,ChangeLigthsActivity::class.java)
            startActivity(intent)
        }

        gardenIrrig.setOnClickListener {
            var intent = Intent(activity,ChangeLigthsActivity::class.java)
            startActivity(intent)
        }

        wake_btn?.setCardBackgroundColor(Color.parseColor("#DCDCD0"))
        night_btn.setOnClickListener( View.OnClickListener() {
            setDefaultButtonsBackground()
            setSelectedViewCardBackground(night_btn)
            activity?.drawerLayout?.setBackgroundResource(R.drawable.night_background)
            setDarkMode(view)
        })
        wake_btn.setOnClickListener( View.OnClickListener() {
            setDefaultButtonsBackground()
            setSelectedViewCardBackground(wake_btn)
            activity?.drawerLayout?.setBackgroundResource(R.drawable.morning)
            setLightMode(view)
        })
        party_btn.setOnClickListener( View.OnClickListener() {
            setDefaultButtonsBackground()
            setSelectedViewCardBackground(party_btn)
            activity?.drawerLayout?.setBackgroundResource(R.drawable.party_final_bg)
            setDarkMode(view)
        })
        relax_btn.setOnClickListener( View.OnClickListener() {
            setDefaultButtonsBackground()
            setSelectedViewCardBackground(relax_btn)
            activity?.drawerLayout?.setBackgroundResource(R.drawable.relax_background)
            setDarkMode(view)
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
        card_text1?.setTextColor(Color.parseColor("#FFFFFF"))
        card_text2?.setTextColor(Color.parseColor("#FFFFFF"))
        card_text3?.setTextColor(Color.parseColor("#FFFFFF"))
        textView5?.setTextColor(Color.parseColor("#FFFFFF"))
        textView4?.setTextColor(Color.parseColor("#FFFFFF"))
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.menu_hamburguer_white)

        val question = (activity as AppCompatActivity).findViewById<ImageButton>(R.id.button)
        question.setImageResource(R.drawable.ic_baseline_question_mark_24_white)
    }

    fun setLightMode(view: View) {
        textView6?.setTextColor(Color.parseColor("#000000"))
        textView3?.setTextColor(Color.parseColor("#000000"))
        card_text1?.setTextColor(Color.parseColor("#000000"))
        card_text2?.setTextColor(Color.parseColor("#000000"))
        card_text3?.setTextColor(Color.parseColor("#000000"))
        textView5?.setTextColor(Color.parseColor("#000000"))
        textView4?.setTextColor(Color.parseColor("#000000"))
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.menu_hamburguer)
        val question = (activity as AppCompatActivity).findViewById<ImageButton>(R.id.button)
        question.setImageResource(R.drawable.ic_baseline_question_mark_24)
    }



}