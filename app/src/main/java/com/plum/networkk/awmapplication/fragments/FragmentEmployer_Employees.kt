package com.plum.networkk.awmapplication.fragments

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.plum.networkk.awmapplication.AppController
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.activities.AddEmployeeActivity
import com.plum.networkk.awmapplication.activities.MenuActivityEmployee
import com.plum.networkk.awmapplication.activities.MenuActivityEmployer
import com.plum.networkk.awmapplication.activities.StatisticsActivity
import com.plum.networkk.awmapplication.adapters.AllEmployeesAdapter
import com.plum.networkk.awmapplication.adapters.TakeBreakAdapter
import com.plum.networkk.awmapplication.data_model.Employee
import com.plum.networkk.awmapplication.data_model.EmployeeData
import com.plum.networkk.awmapplication.models.TakeBreakDataModel

class FragmentEmployer_Employees : Fragment() {


    var allEmployeesList = ArrayList<EmployeeData>()
    lateinit var allEmployeesRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_employer_employees, container, false)

        /**********************************************
         * Add Employee Button Click Event Handler
         */
        var addTxtButton =   (activity as MenuActivityEmployer).findViewById(R.id.iconAddEmployee) as View;
        addTxtButton.setOnClickListener() {
            Log.e("TAG", "Add Employee Clicked")

            var addEmployeeIntent =
                Intent(requireActivity(), AddEmployeeActivity::class.java)
            startActivity(addEmployeeIntent)
        }

        /**************************************
         * Setup Employee List
         */
        allEmployeesRecyclerView =
            root.findViewById(R.id.allEmployeesRecyclerView) as RecyclerView

        allEmployeesList.addAll(AppController.employeeDataList)
        var activeEmployeesAdapter =
            AllEmployeesAdapter(requireActivity(), allEmployeesList)
        allEmployeesRecyclerView.adapter = activeEmployeesAdapter
//        root.findViewById<View>(R.id.addEmployee).setOnClickListener{
//            val shareIntent = Intent()
//            shareIntent.action = Intent.ACTION_SEND
//            shareIntent.type="text/plain"
//            shareIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
//            startActivity(Intent.createChooser(shareIntent,"Share"))
//        }

        return root
    }


    fun takeBreakDialog() {
        val dialog = Dialog(requireActivity()/*, R.style.SearchFieldSetterDialog*/)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val window: Window = dialog.window!!
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog.setContentView(R.layout.take_break_dialog)
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val tvCancel = dialog.findViewById(R.id.tvCancel) as TextView
        val btnGoOnBreak = dialog.findViewById(R.id.btnGoOnBreak) as RelativeLayout
        val breakTypeRecyclerView = dialog.findViewById(R.id.breakTypeRecyclerView) as RecyclerView

        var breakDataList = ArrayList<TakeBreakDataModel>()
        breakDataList.add(
            TakeBreakDataModel(
                "10 Minute break",
                "1 of 2 Remaining",
                "10 Mins"
            )
        )
        breakDataList.add(
            TakeBreakDataModel(
                "Lunch break",
                "1 of 1 Remaining",
                "20 Mins"
            )
        )

        breakDataList.add(
            TakeBreakDataModel(
                "Emergency break",
                "1 of 1 Remaining",
                "30 Mins"
            )
        )
        var takeBreakAdapter = TakeBreakAdapter(requireActivity(), breakDataList)

//        takeBreakAdapter?.setDataToList(breakDataList)
        breakTypeRecyclerView.adapter = takeBreakAdapter



        tvCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnGoOnBreak.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }


    fun advertisementDialog() {
        val dialog = Dialog(requireActivity()/*, R.style.SearchFieldSetterDialog*/)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val window: Window = dialog.window!!
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog.setContentView(R.layout.employee_dashboard_advertisement_dialog)
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        val btnCross = dialog.findViewById(R.id.btnCross) as ImageView
        val btnGoBack = dialog.findViewById(R.id.btnGoBack) as RelativeLayout


        btnCross.setOnClickListener {
            dialog.dismiss()
        }

        btnGoBack.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onResume() {
        super.onResume()
        if(this::allEmployeesRecyclerView.isInitialized){
            allEmployeesList.clear()
            allEmployeesList.addAll(AppController.employeeDataList)
            var activeEmployeesAdapter =
                AllEmployeesAdapter(requireActivity(), allEmployeesList)
            allEmployeesRecyclerView.adapter = activeEmployeesAdapter
        }
    }

}