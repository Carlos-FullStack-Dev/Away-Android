package com.plum.networkk.awmapplication.models

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.Resolve
import com.mindorks.placeholderview.annotations.View
import com.mindorks.placeholderview.annotations.expand.Collapse
import com.mindorks.placeholderview.annotations.expand.Expand
import com.mindorks.placeholderview.annotations.expand.Parent
import com.mindorks.placeholderview.annotations.expand.SingleTop
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.activities.FAQActivity
import com.plum.networkk.awmapplication.interfaces.ExpendableListItemClick

@Parent
@SingleTop
@Layout(R.layout.item_list_faqs)
class HeaderViewFAQs(mContext: FAQActivity, faqId: Int, faqTitle: String) {
    @View(R.id.tvFaqItem)
    var tvFaqItem: TextView? = null

    @View(R.id.arrowForward)
    var arrowForward: ImageView? = null

    val mContext: Context
    var faqTitle: String
    var faqId: Int
    var expendableListItemClick: ExpendableListItemClick

    @Resolve
    private fun onResolve() {
        tvFaqItem!!.setText("" + faqTitle)

//        textViewChild!!.setOnClickListener {
//            Toast.makeText(
//                mContext,
//                movie.getName(),
//                Toast.LENGTH_SHORT
//            ).show()
//        }
    }

    @Expand
    private fun onExpand() {
//        Toast.makeText(mContext, "onExpand $mHeaderText", Toast.LENGTH_SHORT).show()

        arrowForward!!.rotation = 90.0f
        if (expendableListItemClick != null) {
            expendableListItemClick.OnListItemExpand(faqId)
        }
    }

    @Collapse
    private fun onCollapse() {
//        Toast.makeText(mContext, "onCollapse $mHeaderText", Toast.LENGTH_SHORT).show()

        arrowForward!!.rotation = 0.0f
        if (expendableListItemClick != null) {
            expendableListItemClick.OnListItemCollapse(faqId)
        }
    }

    companion object {
        private const val TAG = "HeaderView"
    }

    init {
        this.mContext = mContext
        this.faqTitle = faqTitle
        this.expendableListItemClick = mContext
        this.faqId = faqId
    }
}