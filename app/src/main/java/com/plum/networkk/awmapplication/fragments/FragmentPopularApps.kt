package com.plum.networkk.awmapplication.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.RelativeLayout
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.adapters.AddAllowedOrBlockedAppsAdapter
import com.plum.networkk.awmapplication.models.AllowedOrBlockedModel


class FragmentPopularApps : Fragment {
    var appsList = ArrayList<AllowedOrBlockedModel>()
    var adapter: AddAllowedOrBlockedAppsAdapter? = null

    constructor(appsList: ArrayList<AllowedOrBlockedModel>) {
        this.appsList = appsList
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root: View =
            inflater.inflate(R.layout.fragment_popular_apps, container, false)

        var appSearchView = root.findViewById<SearchView>(R.id.appSearchView)
        var appsRecyclerView = root.findViewById<RecyclerView>(R.id.appsRecyclerView)
        var btnAddToSelectedAppsList =
            root.findViewById<RelativeLayout>(R.id.btnAddToSelectedAppsList)

        if (appsList != null && appsList.size > 0) {
            adapter =
                AddAllowedOrBlockedAppsAdapter(requireActivity(), appsList)
            appsRecyclerView.adapter = adapter
        }


        appSearchView.setImeOptions(EditorInfo.IME_ACTION_DONE);


        appSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
//                if (appsList.contains(query)) {
//                    adapter.getFilter().filter(query);
//                } else {
//                    Toast.makeText(requireActivity(), "No Match found", Toast.LENGTH_LONG).show();
//                }
                return false;
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter!!.getFilter().filter(newText);
                return false;
            }

        })

        return root
    }


}