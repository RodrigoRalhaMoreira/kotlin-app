package com.example.futurouse

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.futurouse.databinding.ActivityKitchenBinding
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