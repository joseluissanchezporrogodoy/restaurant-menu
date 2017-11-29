package com.joseluissanchez_porrogodoy.restaurantmenus.fragment

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.joseluissanchez_porrogodoy.restaurantmenus.R
import com.joseluissanchez_porrogodoy.restaurantmenus.adapter.PlatesRecyclerViewAdapter
import com.joseluissanchez_porrogodoy.restaurantmenus.model.CloudPlates

/**
 * Created by joseluissanchez-porrogodoy on 27/11/2017.
 */
class CloudPlateListFragment: Fragment() {
    companion object {
        fun newInstance() = MenuFragment()
    }
    lateinit var plateList: RecyclerView
    lateinit var root: View
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        if (inflater != null){
            root = inflater.inflate(R.layout.fragment_cloudplate,container,false)
            plateList = root.findViewById(R.id.menu_list)
            plateList.layoutManager = LinearLayoutManager(activity)
            plateList.itemAnimator = DefaultItemAnimator()
            //Adapter
            plateList.adapter = PlatesRecyclerViewAdapter(CloudPlates.plates)

        }

        return root
    }


}
