package com.example.futurouse

import android.animation.ObjectAnimator
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.navigation.NavigationView
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import me.tankery.lib.circularseekbar.CircularSeekBar
import java.util.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var blindsPerc = arrayOf("0%", "25%", "50%", "75%", "100%")
    var currentBlindsIndex = 0

    var hour = 0
    var minute = 0
    lateinit var button : CardView

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
        toggle.drawerArrowDrawable.color = Color.BLACK
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

    fun garden(view: View){
        setContentView(R.layout.irrigation)
        button = findViewById<CardView>(R.id.button)
    }

    fun timepick(view: View){
        val onTimeSetListener =
            OnTimeSetListener { timePicker, i, i1 ->
                hour = i
                minute = i1
                val text = findViewById<TextView>(R.id.textView)
                text.text = String.format(Locale.getDefault(),"%02d:%02d",hour,minute)
            }

        var timePickerDialog = TimePickerDialog(this,onTimeSetListener,hour,minute,true)
        timePickerDialog.setTitle("Select time")
        timePickerDialog.show()
    }
}
