package com.example.futurouse

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.choose_room.*

class Rooms: Fragment(R.layout.choose_room){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        kitchenButton.setOnClickListener {
            Intent(activity, KitchenActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}