package com.example.futurouse

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.futurouse.oven.OvenActivity
import kotlinx.android.synthetic.main.activity_kitchen.*

class KitchenActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_kitchen)

        ovenButton.setOnClickListener {
            Intent(this, OvenActivity::class.java).also {
                startActivity(it)
            }
        }

        backButton.setOnClickListener { this.finish() }

    }
}