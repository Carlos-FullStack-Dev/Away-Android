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
import com.plum.networkk.awmapplication.activities.EmployeeDetailActivity
import com.plum.networkk.awmapplication.activities.EmployeeSettingActivity
import com.plum.networkk.awmapplication.models.ActiveEmployeesDataModel
import com.plum.networkk.awmapplication.models.EmployeeDetailModel
import com.plum.networkk.awmapplication.models.TakeBreakDataModel


class EmployeesDetailsAdapter(context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var dataList: ArrayList<EmployeeDetailModel>? = null
    var mContext: Activity? = null

    constructor(mContext: Activity, breakDataList: ArrayList<EmployeeDetailModel>) : this(
        mContext
    ) {
//        super(mContext,breakDataList)
        this.mContext = mContext
        dataList = breakDataList
    }


//    var onGridItemClick: GridItemClick? = null

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.item_list_employee_details,
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


        ViewHolderM.tvItemTitle.text = dataList!!.get(position).itemTitle
        ViewHolderM.tvItemValue.text = dataList!!.get(position).itemValue
        ViewHolderM.arrowForward.visibility = dataList!!.get(position).arrowForward!!
        ViewHolderM.itemLineView.visibility = dataList!!.get(position).lineVisible!!


    }

    fun setDataToList(mList: ArrayList<EmployeeDetailModel>) {
        this.dataList = mList
//        notifyDataSetChanged()
    }

    inner class ViewHolderM(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        override fun onClick(v: View?) {
            if (adapterPosition == 4) {
                var employeesSettings = Intent(mContext, EmployeeSettingActivity::class.java)
                mContext!!.startActivity(employeesSettings)
            }
        }

        var tvItemTitle = itemView.findViewById(R.id.tvItemTitle) as TextView
        var tvItemValue = itemView.findViewById(R.id.tvItemValue) as TextView
        var arrowForward = itemView.findViewById(R.id.arrowForward) as ImageView
        var itemLineView = itemView.findViewById(R.id.itemLineView) as View


        init {
            itemView.setOnClickListener(this)
        }
    }
}
