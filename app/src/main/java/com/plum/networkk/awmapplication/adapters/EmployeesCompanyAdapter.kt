package com.plum.networkk.awmapplication.adapters


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.Resource
import com.plum.networkk.awmapplication.AppController
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.activities.MenuActivityEmployee
import com.plum.networkk.awmapplication.data_model.Company
import com.plum.networkk.awmapplication.database.msharedpreference.MySharedPreferences


class EmployeesCompanyAdapter(context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onItemClick: ((Company) -> Unit)? = null
    var dataList: ArrayList<Company>? = null
    var mContext: Activity? = null

    constructor(mContext: Activity, DataList: ArrayList<Company>) : this(
        mContext
    ) {
//        super(mContext,breakDataList)
        this.mContext = mContext
        dataList = DataList
    }


//    var onGridItemClick: GridItemClick? = null

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.item_list_companies,
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


        ViewHolderM.tvCompanyName.text = dataList!!.get(position).name
        var seletedCompany = AppController.selectedCompany
        if(seletedCompany!!.companyID == dataList!!.get(position).companyID) {
            ViewHolderM.tvCompanyName.setTextColor(Color.parseColor("#84d033"))
        } else {
            ViewHolderM.tvCompanyName.setTextColor(Color.parseColor("#000000"))
        }
    }

    fun setDataToList(mList: ArrayList<Company>) {
        this.dataList = mList
//        notifyDataSetChanged()
    }


    inner class ViewHolderM(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        override fun onClick(v: View?) {
            /** Update current selected Company ****/
            AppController.selectedCompany = dataList!!.get(adapterPosition)

            /**  Save Company ID into device ****/
            var companyID = dataList!!.get(adapterPosition).companyID.toString()
            MySharedPreferences.editStringPreferences(MySharedPreferences.COMPANY_ID,  companyID , mContext!!)

            /** Back Dashboard Page ****/
            var menuPage = Intent(mContext, MenuActivityEmployee::class.java)
            mContext!!.startActivity(menuPage)
            mContext!!.finish()
        }

        var tvCompanyName: TextView = itemView.findViewById(R.id.tvCompanyName)
        var cvCardView: View = itemView.findViewById(R.id.listItemmainLayout)
        init {
            itemView.setOnClickListener(this)
        }
    }
}
