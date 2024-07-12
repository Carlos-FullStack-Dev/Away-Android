package com.plum.networkk.awmapplication.adapters


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.activities.EmployeeAwaySettingActivity
import com.plum.networkk.awmapplication.activities.EmployeeBreakSettingActivity
import com.plum.networkk.awmapplication.activities.EmployeeNotificationSettingActivity
import com.plum.networkk.awmapplication.activities.EmployeeWifiSettingActivity
import com.plum.networkk.awmapplication.models.EmployeeSettingModel
import com.plum.networkk.awmapplication.models.EmployeeWifiSettingModel


class EmployeesWifiSettingAdapter(context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_HEADER = 0
    private val TYPE_NORMAL = 1

    var dataList: ArrayList<EmployeeWifiSettingModel>? = null
    var mContext: Activity? = null

    constructor(mContext: Activity, breakDataList: ArrayList<EmployeeWifiSettingModel>) : this(
        mContext
    ) {
//        super(mContext,breakDataList)
        this.mContext = mContext
        dataList = breakDataList
    }


//    var onGridItemClick: GridItemClick? = null

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TYPE_HEADER
            4 -> TYPE_HEADER
            else -> TYPE_NORMAL
        }

//        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {


        when (viewType) {
            TYPE_HEADER -> {
                val v = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_list_header_wifi_setting,
                    parent, false
                )
                var viewHolderh = ViewHolderHeader(v)
                return viewHolderh
            }
            else -> {
                val v = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_list_employee_wifi_settings,
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
                viewHolderH.tvHeaderTitle.text = dataList!!.get(position).settingTitle
                viewHolderH.tvAddNew.visibility = dataList!!.get(position).isHeaderBtnVisible
                viewHolderH.tvAddNew.setOnClickListener {

                }
            }

            else -> {//TYPE_NORMAL

                val ViewHolderM = holder as ViewHolderM
                ViewHolderM.tvSettingTitle.text = dataList!!.get(position).settingTitle
                ViewHolderM.switchCheckbox.isChecked = dataList!!.get(position).isChecked!!
                ViewHolderM.switchCheckbox.visibility =
                    dataList!!.get(position).isSwitchBtnVisible!!
            }

        }


    }

    fun setDataToList(mList: ArrayList<EmployeeWifiSettingModel>) {
        this.dataList = mList
//        notifyDataSetChanged()
    }

    inner class ViewHolderM(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        override fun onClick(v: View?) {
        }

        var tvSettingTitle = itemView.findViewById(R.id.tvSettingTitle) as TextView
        var switchCheckbox = itemView.findViewById(R.id.switchCheckbox) as CheckBox

        init {
            itemView.setOnClickListener(this)
        }
    }


    inner class ViewHolderHeader(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        var tvHeaderTitle = itemView.findViewById(R.id.tvHeaderTitle) as TextView
        var tvAddNew = itemView.findViewById(R.id.tvAddNew) as TextView

        init {
        }
    }
}
