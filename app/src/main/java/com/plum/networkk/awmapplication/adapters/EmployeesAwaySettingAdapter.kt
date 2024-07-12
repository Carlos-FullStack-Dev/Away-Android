package com.plum.networkk.awmapplication.adapters


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.activities.AddGpsLocationMapActivity
import com.plum.networkk.awmapplication.models.EmployeeAwaySettingModel
import com.plum.networkk.awmapplication.models.EmployeeSettingModel


class EmployeesAwaySettingAdapter(context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_HEADER = 0
    private val TYPE_NORMAL = 1

    var dataList: ArrayList<EmployeeAwaySettingModel>? = null
    var mContext: Activity? = null

    constructor(mContext: Activity, breakDataList: ArrayList<EmployeeAwaySettingModel>) : this(
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
                    R.layout.item_list_header_away_setting,
                    parent, false
                )
                var viewHolderh = ViewHolderHeader(v)
                return viewHolderh
            }
            else -> {
                val v = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_list_employee_away_settings,
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
                viewHolderH.tvAddNew.text = dataList!!.get(position).txtDistance
                viewHolderH.tvAddNew.setOnClickListener {

                    if (viewHolderH.tvHeaderTitle.text != null && position == 4) {
                        var mapIntent = Intent(mContext, AddGpsLocationMapActivity::class.java)
                        mContext!!.startActivity(mapIntent)
                    }

                }
            }

            else -> {//TYPE_NORMAL

                val ViewHolderM = holder as ViewHolderM
                ViewHolderM.tvItemTitle.text = dataList!!.get(position).itemTitle
                ViewHolderM.tvDistance.text = dataList!!.get(position).txtDistance
                ViewHolderM.switchCheckbox.isSelected = dataList!!.get(position).isSwitchOn!!

                ViewHolderM.tvDistance.visibility = dataList!!.get(position).isDistanceVissible!!
                ViewHolderM.switchCheckbox.visibility = dataList!!.get(position).isSwitchVissible!!
                ViewHolderM.distanceSeekbar.visibility = dataList!!.get(position).isSeekbarVissible

            }

        }


    }

    fun setDataToList(mList: ArrayList<EmployeeAwaySettingModel>) {
        this.dataList = mList
//        notifyDataSetChanged()
    }

    inner class ViewHolderM(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        override fun onClick(v: View?) {
//            var employeesDetails = Intent(mContext, EmployeeDetailActivity::class.java)
//            mContext!!.startActivity(employeesDetails)
        }

        var tvItemTitle = itemView.findViewById(R.id.tvItemTitle) as TextView
        var tvDistance = itemView.findViewById(R.id.tvDistance) as TextView
        var switchCheckbox = itemView.findViewById(R.id.switchCheckbox) as CheckBox
        var distanceSeekbar = itemView.findViewById(R.id.distanceSeekbar) as SeekBar

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
