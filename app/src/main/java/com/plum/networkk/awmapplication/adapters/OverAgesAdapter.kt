package com.plum.networkk.awmapplication.adapters


import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.models.AddorEditBreakModel


class OverAgesAdapter(context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_HEADER = 0
    private val TYPE_NORMAL = 1

    var dataList: ArrayList<AddorEditBreakModel>? = null
    var mContext: Activity? = null

    constructor(mContext: Activity, breakDataList: ArrayList<AddorEditBreakModel>) : this(
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
            else -> TYPE_NORMAL
        }

//        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {


        when (viewType) {
            TYPE_HEADER -> {
                val v = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_list_overages_header,
                    parent, false
                )
                var viewHolderh = ViewHolderHeader(v)
                return viewHolderh
            }
            else -> {
                val v = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_list_add_edit_break,
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
                viewHolderH.tvHeaderTitle.text = dataList!!.get(position).tvTitle
            }

            else -> {//TYPE_NORMAL

                val ViewHolderM = holder as ViewHolderM
                ViewHolderM.tvTitle.text = dataList!!.get(position).tvTitle
                ViewHolderM.tvBreakTime.text = dataList!!.get(position).breakTimeString
                ViewHolderM.timeSeekbar.progress = dataList!!.get(position).breakTimeInMinutes!!
                ViewHolderM.timeSeekbar.visibility = dataList!!.get(position).isSeekBarVisible!!
            }

        }


    }

    fun setDataToList(mList: ArrayList<AddorEditBreakModel>) {
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

        var tvTitle = itemView.findViewById(R.id.tvTitle) as TextView
        var tvBreakTime = itemView.findViewById(R.id.tvOverAgesValue) as TextView
        var timeSeekbar = itemView.findViewById(R.id.timeSeekbar) as SeekBar

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
