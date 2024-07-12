package com.plum.networkk.awmapplication.models

import android.content.Context
import android.widget.TextView
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.Resolve
import com.mindorks.placeholderview.annotations.View
import com.plum.networkk.awmapplication.R


@Layout(R.layout.item_list_overages_submenu)
class ChildView(mContext: Context, dataModel: OverAgesChildDataModel) {
    @View(R.id.tvDate)
    var textViewChild: TextView? = null

    @View(R.id.tvTime)
    var tvTime: TextView? = null

    @View(R.id.tvUnlocks)
    var tvUnlocks: TextView? = null

    val mContext: Context
    var dataModel: OverAgesChildDataModel

    @Resolve
    private fun onResolve() {
        textViewChild!!.setText(""+dataModel.date)
        tvTime!!.setText(""+dataModel.time)
        tvUnlocks!!.setText(""+dataModel.excusesOverAgesValue)

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