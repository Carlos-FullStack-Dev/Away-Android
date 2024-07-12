package com.plum.networkk.awmapplication.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.activities.StatisticsActivity_Employer
import com.plum.networkk.awmapplication.interfaces.RecyclerItemClick
import com.plum.networkk.awmapplication.models.ChartWeeksModel


class Employer_Adapter_Statistics(context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_HEADER = 0
    private val TYPE_NORMAL = 1

    var dataList: ArrayList<ChartWeeksModel>? = null
    var recyclerItemClick: RecyclerItemClick? = null
    var mActivityContext: StatisticsActivity_Employer? = null

    constructor(
        mActivityContext: StatisticsActivity_Employer,
        breakDataList: ArrayList<ChartWeeksModel>
    ) : this(
        mActivityContext
    ) {
//        super(mContext,breakDataList)
//        this.recyclerItemClick = mActivityContext
        this.mActivityContext = mActivityContext
        dataList = breakDataList
    }


//    var onGridItemClick: GridItemClick? = null

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.item_list_weeks,
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
        ViewHolderM.tvItemValue.text = dataList!!.get(position).weekValue.toString()

    }

    fun setDataToList(mList: ArrayList<ChartWeeksModel>) {
        this.dataList = mList
//        notifyDataSetChanged()
    }

    inner class ViewHolderM(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        override fun onClick(v: View?) {
            recyclerItemClick!!.OnRecyclerViewItemClick(adapterPosition)
        }

        var tvItemValue = itemView.findViewById(R.id.tvItemValue) as TextView


        init {
            itemView.setOnClickListener(this)
        }
    }

}
