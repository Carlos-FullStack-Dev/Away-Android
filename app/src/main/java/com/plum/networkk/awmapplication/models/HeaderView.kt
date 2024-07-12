package com.plum.networkk.awmapplication.models

import android.content.Context
import android.widget.TextView
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.Resolve
import com.mindorks.placeholderview.annotations.View
import com.mindorks.placeholderview.annotations.expand.Collapse
import com.mindorks.placeholderview.annotations.expand.Expand
import com.mindorks.placeholderview.annotations.expand.Parent
import com.mindorks.placeholderview.annotations.expand.SingleTop
import com.plum.networkk.awmapplication.R


@Parent
@SingleTop
@Layout(R.layout.item_list_overages_header)
class HeaderView(context: Context, overAgesDataModel: OverAgesDataModel) {

    @View(R.id.tvItemTitle)
    var tvItemTitle: TextView? = null

    @View(R.id.tvOverAgesValue)
    var tvOverAgesValue: TextView? = null


    private val mContext: Context
    private val overAgesDataModel: OverAgesDataModel

    @Resolve
    private fun onResolve() {
        tvItemTitle!!.text = overAgesDataModel.employeeName
        tvOverAgesValue!!.text = overAgesDataModel.overagesValue
    }

    @Expand
    private fun onExpand() {
//        Toast.makeText(mContext, "onExpand $mHeaderText", Toast.LENGTH_SHORT).show()
    }

    @Collapse
    private fun onCollapse() {
//        Toast.makeText(mContext, "onCollapse $mHeaderText", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "HeaderView"
    }

    init {
        mContext = context
        this.overAgesDataModel = overAgesDataModel
    }
}