package com.plum.networkk.awmapplication.adapters


import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.models.AllowedOrBlockedModel


class AddAllowedOrBlockedAppsAdapter(context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    var appsListFull = ArrayList<AllowedOrBlockedModel>()
    var appsList: ArrayList<AllowedOrBlockedModel>? = null
    var mActivityContext: Activity? = null

    constructor(
        mActivityContext: Activity,
        breakappsList: ArrayList<AllowedOrBlockedModel>
    ) : this(
        mActivityContext
    ) {
//        super(mContext,breakappsList)
        this.mActivityContext = mActivityContext
        appsList = breakappsList
        appsListFull = ArrayList(appsList!!)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.item_list_allowed_blocked_apps,
            parent, false
        )
        var viewHolder = ViewHolderM(v)
        return viewHolder


    }

    override fun getItemCount(): Int {
//        return 3
        if (appsList != null && appsList!!.size > 0)
            return appsList!!.size
        else
            return 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val ViewHolderM = holder as ViewHolderM

        ViewHolderM.appIcon.setBackgroundResource(appsList!!.get(position).appIcon!!)
        ViewHolderM.tvAppTitle.text = appsList!!.get(position).appTitle
        ViewHolderM.switchCheckbox.isChecked = appsList!!.get(position).isChecked!!

    }

    fun setDataToList(mList: ArrayList<AllowedOrBlockedModel>) {
        this.appsList = mList

//        notifyDataSetChanged()
    }

    inner class ViewHolderM(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        override fun onClick(v: View?) {}

        var appIcon = itemView.findViewById(R.id.appIcon) as ImageView
        var tvAppTitle = itemView.findViewById(R.id.tvAppTitle) as TextView
        var switchCheckbox = itemView.findViewById(R.id.switchCheckbox) as CheckBox

        init {
            itemView.setOnClickListener(this)
        }
    }


    private val exampleFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: MutableList<AllowedOrBlockedModel> = ArrayList()
            if (constraint == null || constraint.length == 0) {
                filteredList.addAll(appsListFull)
            } else {
                val filterPattern =
                    constraint.toString().toLowerCase().trim { it <= ' ' }
                for (item in appsListFull) {
                    if (item.appTitle!!.toLowerCase().contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(
            constraint: CharSequence,
            results: FilterResults
        ) {
            appsList!!.clear()
            appsList!!.addAll(results.values as List<AllowedOrBlockedModel>)
            notifyDataSetChanged()
        }
    }

    override fun getFilter(): Filter {
        return exampleFilter!!
    }


}
