package com.example.futurouse.oven

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.futurouse.R

class OvenMode : Fragment(R.layout.fragment_oven_mode) {

    var modes = arrayOf(
        R.id.imageMode1,
        R.id.imageMode2,
        R.id.imageMode3,
        R.id.imageMode4,
        R.id.imageMode5,
        R.id.imageMode6,
        R.id.imageMode7,
        R.id.imageMode8
    )

    var selectedModes = arrayOf(
        R.id.oven_mode_selected1,
        R.id.oven_mode_selected2,
        R.id.oven_mode_selected3,
        R.id.oven_mode_selected4,
        R.id.oven_mode_selected5,
        R.id.oven_mode_selected6,
        R.id.oven_mode_selected7,
        R.id.oven_mode_selected8
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners(view, view.findViewById(modes[0]), view.findViewById(selectedModes[0]))
        setListeners(view, view.findViewById(modes[1]), view.findViewById(selectedModes[1]))
        setListeners(view, view.findViewById(modes[2]), view.findViewById(selectedModes[2]))
        setListeners(view, view.findViewById(modes[3]), view.findViewById(selectedModes[3]))
        setListeners(view, view.findViewById(modes[4]), view.findViewById(selectedModes[4]))
        setListeners(view, view.findViewById(modes[5]), view.findViewById(selectedModes[5]))
        setListeners(view, view.findViewById(modes[6]), view.findViewById(selectedModes[6]))
        setListeners(view, view.findViewById(modes[7]), view.findViewById(selectedModes[7]))
    }

    fun setListeners(view: View, mode: ImageView, modeSelected: ImageView) {

        mode.setOnClickListener {

            val previousSelectedId = selectedModes.find {
                view.findViewById<ImageView>(it).visibility == View.VISIBLE
            }
            val previousSelected = previousSelectedId?.let { it1 -> view.findViewById<ImageView>(it1) }
            previousSelected?.visibility = View.INVISIBLE

            val previousModeId = modes[selectedModes.indexOf(previousSelectedId)]
            val previousMode = view.findViewById<ImageView>(previousModeId)
            previousMode.visibility = View.VISIBLE

            mode.visibility = View.INVISIBLE
            modeSelected.visibility = View.VISIBLE

            val currentModeView = activity?.findViewById<ImageView>(R.id.currentMode)
            currentModeView?.setImageDrawable(mode.drawable)

        }

    }
}