package com.plum.networkk.awmapplication.adapters


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.activities.*
import com.plum.networkk.awmapplication.fragments.FragmentEmployer_Setting
import com.plum.networkk.awmapplication.models.EmployeeSettingModel
import com.plum.networkk.awmapplication.models.EmployerSettingModel


class EmployerSettingAdapter(context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_HEADER = 0
    private val TYPE_NORMAL = 1

    var dataList: ArrayList<EmployerSettingModel>? = null
    var mContext: FragmentEmployer_Setting? = null

    constructor(
        mContext: FragmentEmployer_Setting,
        breakDataList: ArrayList<EmployerSettingModel>
    ) : this(mContext.requireActivity()) {
//        super(mContext,breakDataList)
        this.mContext = mContext
        dataList = breakDataList
    }


//    var onGridItemClick: GridItemClick? = null

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TYPE_HEADER
            5 -> TYPE_HEADER
            8 -> TYPE_HEADER
            else -> TYPE_NORMAL
        }

//        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {


        when (viewType) {
            TYPE_HEADER -> {
                val v = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_list_header,
                    parent, false
                )
                var viewHolderh = ViewHolderHeader(v)
                return viewHolderh
            }
            else -> {
                val v = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_list_employee_settings,
                    parent, false
                )
                var viewHolder = ViewHolderM(v)
                return viewHolder
            }
        }


    }

    override fun getItemCount(): Int {
        if (dataList != null && dataList!!.size > 0)
            return dataList!!.size
        else
            return 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder.getItemViewType()) {
            TYPE_HEADER -> {
                val viewHolderH = holder as ViewHolderHeader
                viewHolderH.tvHeaderTitle.text = dataList!!.get(position).itemTitle
            }

            else -> {//TYPE_NORMAL

                val ViewHolderM = holder as ViewHolderM
                ViewHolderM.tvItemTitle.text = dataList!!.get(position).itemTitle
            }

        }


    }

    fun setDataToList(mList: ArrayList<EmployerSettingModel>) {
        this.dataList = mList
//        notifyDataSetChanged()
    }

    inner class ViewHolderM(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        override fun onClick(v: View?) {

            if (adapterPosition == 1) {
                var businessSettingIntnet =
                    Intent(mContext!!.requireActivity(), BusinessSettingActivity::class.java)
                mContext!!.startActivity(businessSettingIntnet)
            } else if (adapterPosition == 2) {
                var employeesDetails =
                    Intent(mContext!!.requireActivity(), EmployeeAwaySettingActivity::class.java)
                mContext!!.startActivity(employeesDetails)
            } else if (adapterPosition == 3) {
//                employees break screen
//                var employeesDetails =
//                    Intent(mContext!!.requireActivity(), PrivacyPolicyActivity::class.java)
//                mContext!!.startActivity(employeesDetails)
            } else if (adapterPosition == 4) {
                var employeesDetails =
                    Intent(
                        mContext!!.requireActivity(),
                        EmployeeNotificationSettingActivity::class.java
                    )
                mContext!!.startActivity(employeesDetails)
            } else if (adapterPosition == 6) {
                var employeesDetails =
                    Intent(mContext!!.requireActivity(), EmployeeWifiSettingActivity::class.java)
                mContext!!.startActivity(employeesDetails)
            } else if (adapterPosition == 7) {
                var employeesDetails =
                    Intent(mContext!!.requireActivity(), EmployeeAppSettingActivity::class.java)
                mContext!!.startActivity(employeesDetails)
            } else if (adapterPosition == 9) {
                var employeesDetails =
                    Intent(mContext!!.requireActivity(), PrivacyPolicyActivity::class.java)
                mContext!!.startActivity(employeesDetails)
            } else if (adapterPosition == 10) {
                var employeesDetails =
                    Intent(mContext!!.requireActivity(), TermOfServiceActivity::class.java)
                mContext!!.startActivity(employeesDetails)
            } else if (adapterPosition == 11) {
                var employeesDetails =
                    Intent(mContext!!.requireActivity(), BillingDetailsActivity::class.java)
                mContext!!.startActivity(employeesDetails)
            }

        }

        var tvItemTitle = itemView.findViewById(R.id.tvItemTitle) as TextView

        init {
            itemView.setOnClickListener(this)
        }
    }


    inner class ViewHolderHeader(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        var tvHeaderTitle = itemView.findViewById(R.id.tvHeaderTitle) as TextView

        init {
        }
    }
}
