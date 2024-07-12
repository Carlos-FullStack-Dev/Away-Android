package com.plum.networkk.awmapplication.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.activities.MenuActivityEmployee
import com.plum.networkk.awmapplication.activities.MenuActivityEmployer
import com.plum.networkk.awmapplication.interfaces.RecyclerItemClick
import com.plum.networkk.awmapplication.models.NavDrawerDataModel


class EmployeeNavDrawerAdapter(context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var dataList: ArrayList<NavDrawerDataModel>? = null
    var recyclerItemClick: RecyclerItemClick? = null


    constructor(
        mContext: MenuActivityEmployee,
        breakDataList: ArrayList<NavDrawerDataModel>
    ) : this(mContext) {
//        super(mContext,breakDataList)
        dataList = breakDataList
        this.recyclerItemClick = mContext
    }

    constructor(
        mContext: MenuActivityEmployer,
        breakDataList: ArrayList<NavDrawerDataModel>
    ) : this(mContext) {
//        super(mContext,breakDataList)
        dataList = breakDataList
        this.recyclerItemClick = mContext
    }


//    var onGridItemClick: GridItemClick? = null

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.item_list_employee_nav_drawer,
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

        ViewHolderM.tvItemTitle.text = dataList!!.get(position).menuName
        ViewHolderM.tvItemTitle.setTextColor(dataList!!.get(position).itemTextColor!!)


    }

    fun setDataToList(mList: ArrayList<NavDrawerDataModel>) {
        this.dataList = mList
//        notifyDataSetChanged()
    }

    inner class ViewHolderM(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        override fun onClick(v: View?) {
//            itemView.setSelected(true)

            recyclerItemClick!!.OnRecyclerViewItemClick(adapterPosition)

        }

        var tvItemTitle: TextView = itemView.findViewById(R.id.tvItemTitle)

        init {
            itemView.setOnClickListener(this)
        }
    }
}
