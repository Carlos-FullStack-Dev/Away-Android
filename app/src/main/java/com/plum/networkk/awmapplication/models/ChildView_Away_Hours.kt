package com.plum.networkk.awmapplication.models

import android.content.Context
import android.widget.TextView
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.Resolve
import com.mindorks.placeholderview.annotations.View
import com.plum.networkk.awmapplication.R


@Layout(R.layout.item_list_away_hours_submenu)
class ChildView_Away_Hours(mContext: Context, dataModel: AwayHoursChildDataModel) {

    @View(R.id.tvAwayOnOff)
    var tvAwayOnOff: TextView? = null

    @View(R.id.tvTime)
    var tvTime: TextView? = null

    @View(R.id.tvDate)
    var tvDate: TextView? = null

    @View(R.id.tvAddresss)
    var tvAddresss: TextView? = null

    val mContext: Context

    var dataModel: AwayHoursChildDataModel

    @Resolve
    private fun onResolve() {
        tvAwayOnOff!!.setText("" + dataModel.awayStatus)
        tvDate!!.setText("" + dataModel.date)
        tvTime!!.setText("" + dataModel.time)
        tvAddresss!!.setText("" + dataModel.eAddress)

//        textViewChild!!.setOnClickListener {
//            Toast.makeText(
//                mContext,
//                movie.getName(),
//                Toast.LENGTH_SHORT
//            ).show()
//        }
    }

    companion object {
        private const val TAG = "ChildView"
    }

    init {
        this.mContext = mContext
        this.dataModel = dataModel
    }
}