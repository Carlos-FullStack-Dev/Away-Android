package com.plum.networkk.awmapplication.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.models.EmpProfileDataModel
import com.plum.networkk.awmapplication.models.TakeBreakDataModel


class EmployeeProfileAdapter(context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var dataList: ArrayList<EmpProfileDataModel>? = null

    constructor(mContext: Context, breakDataList: ArrayList<EmpProfileDataModel>) : this(mContext) {
//        super(mContext,breakDataList)
        dataList = breakDataList
    }


//    var onGridItemClick: GridItemClick? = null

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.item_list_employee_profile,
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

        ViewHolderM.tvItemTitle.text = dataList!!.get(position).empKey
        ViewHolderM.tvItemValue.text = dataList!!.get(position).keyValue


    }

    fun setDataToList(mList: ArrayList<EmpProfileDataModel>) {
        this.dataList = mList
//        notifyDataSetChanged()
    }

    inner class ViewHolderM(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        override fun onClick(v: View?) {
        }

        var tvItemTitle: TextView = itemView.findViewById(R.id.tvItemTitle)
        var tvItemValue: TextView = itemView.findViewById(R.id.tvItemValue)

        init {
            itemView.setOnClickListener(this)
        }
    }
}
