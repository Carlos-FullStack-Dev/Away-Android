package com.plum.networkk.awmapplication.models

import android.content.Context
import android.widget.TextView
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.Resolve
import com.mindorks.placeholderview.annotations.View
import com.mindorks.placeholderview.annotations.expand.Collapse
import com.mindorks.placeholderview.annotations.expand.Expand
import com.plum.networkk.awmapplication.R


@Layout(R.layout.item_list_faqs_child)
class ChildViewFAQs(mContext: Context, faqDescription: String) {
    @View(R.id.tvFaqItem)
    var tvFaqItem: TextView? = null

    val mContext: Context
    var faqDescription: String

    @Resolve
    private fun onResolve() {
        tvFaqItem!!.setText(""+faqDescription)

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
    }

    @Collapse
    private fun onCollapse() {
//        Toast.makeText(mContext, "onCollapse $mHeaderText", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "ChildView"
    }

    init {
        this.mContext = mContext
        this.faqDescription = faqDescription
    }
}