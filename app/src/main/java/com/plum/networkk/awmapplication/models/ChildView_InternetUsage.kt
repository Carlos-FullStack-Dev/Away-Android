package com.plum.networkk.awmapplication.models

import android.content.Context
import android.widget.TextView
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.Resolve
import com.mindorks.placeholderview.annotations.View
import com.plum.networkk.awmapplication.R


@Layout(R.layout.item_list_internet_submenu)
class ChildView_InternetUsage(mContext: Context, dataModel: InternetUsageChildDataModel) {
    @View(R.id.tvName)
    var tvName: TextView? = null

    @View(R.id.tvTime)
    var tvTime: TextView? = null

    @View(R.id.tvexcuseTimeRange)
    var tvexcuseTimeRange: TextView? = null

    val mContext: Context
    var dataModel: InternetUsageChildDataModel

    @Resolve
    private fun onResolve() {
        tvName!!.setText(""+dataModel.eName)
        tvTime!!.setText(""+dataModel.time)
        tvexcuseTimeRange!!.setText(""+dataModel.excuseTimeRange)

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