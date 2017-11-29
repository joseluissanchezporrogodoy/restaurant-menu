package com.joseluissanchez_porrogodoy.restaurantmenus.fragment


import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.joseluissanchez_porrogodoy.restaurantmenus.R
import com.joseluissanchez_porrogodoy.restaurantmenus.adapter.PlatesRecyclerViewAdapter
import com.joseluissanchez_porrogodoy.restaurantmenus.model.Plate


class MenuFragment : Fragment() {
    companion object {
        fun newInstance() = MenuFragment()



    }

    lateinit var plateList: RecyclerView
    lateinit var root: View
    lateinit var list : List<Plate>
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        if (inflater != null){
        root = inflater.inflate(R.layout.fragment_menu,container,false)
            plateList = root.findViewById(R.id.menu_list)
            plateList.layoutManager = LinearLayoutManager(activity)
            plateList.itemAnimator = DefaultItemAnimator()
            // Adapter
            val adapter = PlatesRecyclerViewAdapter(list)
            adapter.onClickListener = View.OnClickListener {
                // Go to detail
                Log.d("Click en plato", "Ir a detalle")
            }
            plateList.adapter = adapter
        }

        return root
    }

}
