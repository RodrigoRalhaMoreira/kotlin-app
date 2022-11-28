package com.example.futurouse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.futurouse.databinding.IrrigationBinding
import kotlinx.android.synthetic.main.activity_garden.*
import kotlinx.android.synthetic.main.activity_garden.backButton

class GardenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_garden)

        backButton.setOnClickListener {
            this.finish()
        }

        irrigation_button.setOnClickListener{
            val intent = Intent(this, IrrigationActivity::class.java)
            startActivity(intent)
        }
    }
}