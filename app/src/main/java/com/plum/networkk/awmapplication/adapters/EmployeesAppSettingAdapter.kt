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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.activities.*
import com.plum.networkk.awmapplication.interfaces.RecyclerItemClick
import com.plum.networkk.awmapplication.models.EmployeeAppSettingModel
import com.plum.networkk.awmapplication.models.EmployeeSettingModel
import com.plum.networkk.awmapplication.models.EmployeeWifiSettingModel
import java.util.*
import kotlin.collections.ArrayList


class EmployeesAppSettingAdapter(context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_HEADER = 0
    private val TYPE_NORMAL = 1

    var dataList: ArrayList<EmployeeAppSettingModel>? = null
    var recyclerItemClick: RecyclerItemClick? = null
    var mActivityContext: EmployeeAppSettingActivity? = null

    constructor(
        mActivityContext: EmployeeAppSettingActivity,
        breakDataList: ArrayList<EmployeeAppSettingModel>
    ) : this(
        mActivityContext
    ) {
//        super(mContext,breakDataList)
        this.recyclerItemClick = mActivityContext
        this.mActivityContext = mActivityContext
        dataList = breakDataList
    }


//    var onGridItemClick: GridItemClick? = null

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TYPE_HEADER
            2 -> TYPE_HEADER
            6 -> TYPE_HEADER
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
                    R.layout.item_list_employee_app_settings,
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
                    if (position == 2) {
                        //allowed apps list
                        var AppsListAllowed=Intent(mActivityContext,AddAllowedOrBlockedAppsActivity::class.java)
                        mActivityContext!!.startActivity(AppsListAllowed)
                    } else if (position == 6) {
                        //blocked apps list


                    }
                }
            }

            else -> {//TYPE_NORMAL

                val ViewHolderM = holder as ViewHolderM
                ViewHolderM.tvSettingTitle.text = dataList!!.get(position).settingTitle
                ViewHolderM.switchCheckbox.isChecked = dataList!!.get(position).isChecked!!
                ViewHolderM.overlayLayout.visibility = dataList!!.get(position).isOverLayVisible
                ViewHolderM.switchCheckbox.visibility =
                    dataList!!.get(position).isSwitchBtnVisible!!
            }

        }


    }

    fun setDataToList(mList: ArrayList<EmployeeAppSettingModel>) {
        this.dataList = mList
//        notifyDataSetChanged()
    }

    inner class ViewHolderM(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        override fun onClick(v: View?) {
            recyclerItemClick!!.OnRecyclerViewItemClick(adapterPosition)
        }

        var tvSettingTitle = itemView.findViewById(R.id.tvSettingTitle) as TextView
        var switchCheckbox = itemView.findViewById(R.id.switchCheckbox) as CheckBox
        var appIcon = itemView.findViewById(R.id.appIcon) as ImageView
        var overlayLayout = itemView.findViewById(R.id.overlayLayout) as ConstraintLayout


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
