package com.plum.networkk.awmapplication.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.activities.FAQActivity


class EmployeeHelpFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_employee_help, container, false)

        var spiner = root.findViewById<Spinner>(R.id.supportTopicSpinner)

        val objects: Array<String> =
            requireActivity().getResources().getStringArray(R.array.help_subjects)

        val adapter: ArrayAdapter<*> = ArrayAdapter<Any?>(
            requireActivity(),
            R.layout.spinner_custom_item,
            objects
        )

        spiner.setAdapter(adapter)

//        spiner.setOnItemSelectedListener(this)

        var listItemFaqLayout = root.findViewById<ConstraintLayout>(R.id.listItemFaqLayout)
        listItemFaqLayout.setOnClickListener {
            var faqIntent = Intent(activity, FAQActivity::class.java)
            startActivity(faqIntent)
        }


        return root
    }

}