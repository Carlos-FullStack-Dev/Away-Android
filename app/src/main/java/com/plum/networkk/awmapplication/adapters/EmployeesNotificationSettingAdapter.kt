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
import com.plum.networkk.awmapplication.models.EmployeeBreakSettingModel
import com.plum.networkk.awmapplication.models.EmployeeNotificationSettingModel
import com.plum.networkk.awmapplication.models.EmployeeSettingModel


class EmployeesNotificationSettingAdapter(context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_HEADER = 0
    private val TYPE_NORMAL = 1

    var dataList: ArrayList<EmployeeNotificationSettingModel>? = null
    var mContext: Activity? = null

    constructor(
        mContext: Activity,
        breakDataList: ArrayList<EmployeeNotificationSettingModel>
    ) : this(
        mContext
    ) {
//        super(mContext,breakDataList)
        this.mContext = mContext
        dataList = breakDataList
    }


//    var onGridItemClick: GridItemClick? = null

    override fun getItemViewType(position: Int): Int {
//        return when (position) {
//            0 -> TYPE_HEADER
//            else -> TYPE_NORMAL
//        }

        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.item_list_employee_notification_settings,
            parent, false
        )
        var viewHolder = ViewHolderM(v)
        return viewHolder

    }

    override fun getItemCount(): Int {
        if (dataList != null && dataList!!.size > 0)
            return dataList!!.size
        else
            return 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        val ViewHolderM = holder as ViewHolderM
        ViewHolderM.tvNotificationTitle.text = dataList!!.get(position).notificationTitle
        ViewHolderM.switchCheckbox.isChecked = dataList!!.get(position).isChecked!!

    }


    fun setDataToList(mList: ArrayList<EmployeeNotificationSettingModel>) {
        this.dataList = mList
//        notifyDataSetChanged()
    }

    inner class ViewHolderM(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        override fun onClick(v: View?) {

            if (adapterPosition == 1) {
//                var employeesDetails =
//                    Intent(mContext, EmployeeAwaySettingActivity::class.java)
//                mContext!!.startActivity(employeesDetails)
            }

        }

        var tvNotificationTitle = itemView.findViewById(R.id.tvNotificationTitle) as TextView
        var switchCheckbox = itemView.findViewById(R.id.switchCheckbox) as CheckBox

        init {
            itemView.setOnClickListener(this)
        }
    }
}

