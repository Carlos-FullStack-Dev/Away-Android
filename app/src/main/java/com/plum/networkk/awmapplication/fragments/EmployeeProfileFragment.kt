package com.plum.networkk.awmapplication.fragments

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.plum.networkk.awmapplication.AppController
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.activities.MenuActivityEmployee
import com.plum.networkk.awmapplication.activities.MenuActivityEmployer
import com.plum.networkk.awmapplication.adapters.EmployeeProfileAdapter
import com.plum.networkk.awmapplication.database.msharedpreference.MySharedPreferences
import com.plum.networkk.awmapplication.models.EmpProfileDataModel

class EmployeeProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_employee_profile, container, false)

        /*********************************************
         * Edit Button & Click Event Hanlder
         */
        var signedInAs = MySharedPreferences.getStringPreferences(
            MySharedPreferences.SIGNED_IN_AS_KEY,
            this.requireContext(), ""
        )
        if (signedInAs.equals("Employee")) {

            var editTxtButton =   (activity as MenuActivityEmployee).findViewById(R.id.toolbarEditBtnTv) as View;
            editTxtButton.setOnClickListener() {
                Log.e("TAG", "Edit Button Clicked")
            }

        } else if (signedInAs.equals("Business Manager")) {
            var editTxtButton =   (activity as MenuActivityEmployer).findViewById(R.id.toolbarEditBtnTv) as View;
            editTxtButton.setOnClickListener() {
                Log.e("TAG", "Edit Button Clicked")
            }

            var addEmployeeButton = (activity as MenuActivityEmployer).findViewById(R.id.iconAddEmployee) as View;
            addEmployeeButton.setVisibility(View.GONE);
        }

//        var editTxtButton =   (activity as MenuActivityEmployee).findViewById(R.id.toolbarEditBtnTv) as View;
//        editTxtButton.setOnClickListener() {
//            Log.e("TAG", "Edit Button Clicked")
//        }

        /*************************************
         * Profile Content List
         */
        val employeeProfileRecyclerView =
            root.findViewById(R.id.employeeProfileRecyclerView) as RecyclerView

        var profileDataList = ArrayList<EmpProfileDataModel>()
        profileDataList.add(
            EmpProfileDataModel(
                "First Name",
                AppController.user!!.user.firstName
            )
        )
        profileDataList.add(
            EmpProfileDataModel(
                "Last Name",
                AppController.user!!.user.lastName
            )
        )
        profileDataList.add(
            EmpProfileDataModel(
                "Email",
                AppController.user!!.user.email
            )
        )
//        profileDataList.add(
//            EmpProfileDataModel(
//                "Password",
//                "*******"
//            )
//        )
        profileDataList.add(
            EmpProfileDataModel(
                "Phone",
                (AppController.user!!.user.phone.takeIf { AppController.user!!.user.phone != null } ?: "NA").toString()
            )
        )
//        profileDataList.add(
//            EmpProfileDataModel(
//                "Company",
//                AppController.user!!.user.team.toString()
//            )
//        )

        var profileAdapter = EmployeeProfileAdapter(requireActivity(), profileDataList)
        employeeProfileRecyclerView.adapter = profileAdapter


        var editTv =

        return root
    }

}