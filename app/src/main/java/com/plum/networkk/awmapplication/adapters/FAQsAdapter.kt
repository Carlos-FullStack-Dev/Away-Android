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
import com.plum.networkk.awmapplication.activities.AppUsageActivity
import com.plum.networkk.awmapplication.activities.EmployeeDetailActivity
import com.plum.networkk.awmapplication.apis.FaqItemModel
import com.plum.networkk.awmapplication.models.ActiveEmployeesDataModel
import com.plum.networkk.awmapplication.models.AllEmployeesDataModel
import com.plum.networkk.awmapplication.models.TakeBreakDataModel


class FAQsAdapter(context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var dataList: ArrayList<FaqItemModel>? = null
    var mContext: Activity? = null

    constructor(mContext: Activity, dataList: ArrayList<FaqItemModel>) : this(
        mContext
    ) {
//        super(mContext,breakDataList)
        this.mContext = mContext
        this.dataList = dataList
    }


//    var onGridItemClick: GridItemClick? = null

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.item_list_faqs,
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

        ViewHolderM.tvFaqItem.text = dataList!!.get(position).name

    }

    fun setDataToList(mList: ArrayList<FaqItemModel>) {
        this.dataList = mList
//        notifyDataSetChanged()
    }

    inner class ViewHolderM(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        override fun onClick(v: View?) {
//            var employeesDetails = Intent(mContext, EmployeeDetailActivity::class.java)
//            mContext!!.startActivity(employeesDetails)

            var openAppUsageActivity =
                Intent(mContext, AppUsageActivity::class.java)
            mContext!!.startActivity(openAppUsageActivity)
        }
        var tvFaqItem: TextView = itemView.findViewById(R.id.tvFaqItem)

        init {
            itemView.setOnClickListener(this)
        }
    }
}
