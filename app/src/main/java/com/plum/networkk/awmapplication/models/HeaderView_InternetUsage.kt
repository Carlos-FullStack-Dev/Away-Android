package com.plum.networkk.awmapplication.models

import android.content.Context
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
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
@Layout(R.layout.item_list_internet_usage_header)
class HeaderView_InternetUsage(context: Context, internetUsageDataModel: InternetUsageDataModel) {

    @View(R.id.tvWebsiteTitle)
    var tvWebsiteTitle: TextView? = null

    @View(R.id.tvOverAgesValue)
    var tvOverAgesValue: TextView? = null

    @View(R.id.itemHeadersView)
    var itemHeadersView: ConstraintLayout? = null

    @View(R.id.tvHeaderTitle)
    var tvHeaderTitle: TextView? = null


    private val mContext: Context
    private val internetUsageDataModel: InternetUsageDataModel

    @Resolve
    private fun onResolve() {
        tvWebsiteTitle!!.text = internetUsageDataModel.tvWebsiteTitle
        tvHeaderTitle!!.text = internetUsageDataModel.headerTitle
        itemHeadersView!!.visibility = internetUsageDataModel.headerTitleVisibility!!
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
        this.internetUsageDataModel = internetUsageDataModel
    }
}