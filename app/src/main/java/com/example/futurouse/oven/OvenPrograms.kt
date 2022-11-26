package com.example.futurouse.oven

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.futurouse.R
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.fragment_oven_programs.*

class OvenPrograms : Fragment(R.layout.fragment_oven_programs) {

    var settings = arrayListOf<MaterialCardView>()
    var checkedSetting: MaterialCardView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settings.addAll(arrayListOf(setting1, setting2, setting3, setting4))

        this.setListeners()
    }

    private fun setListeners() {
        for (setting in settings) {
            setting.setOnLongClickListener {
                if (checkedSetting != null) {
                    checkedSetting!!.isChecked = false
                }
                setting.isChecked = !setting.isChecked
                checkedSetting = setting
                true
            }
        }
    }
}

