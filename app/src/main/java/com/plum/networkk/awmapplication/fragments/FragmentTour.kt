package com.plum.networkk.awmapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.plum.networkk.awmapplication.R

class FragmentTour : Fragment {
    var titleID: String? = null
    var descriptionID: String? = null
    var sliderImageID: Int = 0

    constructor(titleID: String, descriptionID: String, sliderImageID: Int) {
        this.titleID = titleID
        this.descriptionID = descriptionID
        this.sliderImageID = sliderImageID
    }


//    constructor(titleID: Int, descriptionID: Int, sliderImageID: Int) {
//        this.titleID = titleID
//        this.descriptionID = descriptionID
//        this.sliderImageID = sliderImageID
//    }


//    fun FragmentIntro(titleID: Int, descriptionID: Int, sliderImageID: Int) {
//        this.titleID = titleID
//        this.descriptionID = descriptionID
//        this.sliderImageID = sliderImageID
//    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root: View =
            inflater.inflate(R.layout.tour_fragment, container, false)
        val imageViewSlider =
            root.findViewById<ImageView>(R.id.imageViewSlider)
        val textViewTitle = root.findViewById<TextView>(R.id.textViewTitle)
        val textViewDescription = root.findViewById<TextView>(R.id.textViewDescription)
        imageViewSlider.setImageResource(sliderImageID)

        textViewTitle.text = titleID.toString()
        textViewDescription.text = descriptionID.toString()

//        Glide.with(this)
//            .load(sliderImageID)
//            .into(imageViewSlider)
//        Glide.with(this)
//            .load(sliderImageID)
//            .into(imageViewSlider);
//        textViewTitle.setText(titleID)
//        textViewDescription.setText(descriptionID)
        return root
    }
}