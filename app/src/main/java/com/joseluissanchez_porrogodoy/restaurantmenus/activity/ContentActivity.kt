package com.joseluissanchez_porrogodoy.restaurantmenus.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.View
import com.joseluissanchez_porrogodoy.restaurantmenus.DETAIL_MODE
import com.joseluissanchez_porrogodoy.restaurantmenus.MODE
import com.joseluissanchez_porrogodoy.restaurantmenus.fragment.TablesListFragment
import com.joseluissanchez_porrogodoy.restaurantmenus.R
import com.joseluissanchez_porrogodoy.restaurantmenus.adapter.PlatesRecyclerViewAdapter
import com.joseluissanchez_porrogodoy.restaurantmenus.fragment.PlateDetailFragment
import com.joseluissanchez_porrogodoy.restaurantmenus.fragment.PlatesListFragment
import com.joseluissanchez_porrogodoy.restaurantmenus.model.CloudPlates
import com.joseluissanchez_porrogodoy.restaurantmenus.model.Plate
import com.joseluissanchez_porrogodoy.restaurantmenus.model.Table
import com.joseluissanchez_porrogodoy.restaurantmenus.model.Tables

class ContentActivity : AppCompatActivity(), TablesListFragment.OnTableSelectedListener, PlatesRecyclerViewAdapter.OnPlateSelectedListener {

    val addButton: FloatingActionButton by lazy { findViewById<FloatingActionButton>(R.id.add_plate_button) }
    var tablePosition: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)
        // Comprobamos que en la interfaz tenemos un FrameLayout llamado city_list_fragment
        if (findViewById<View>(R.id.main_content) != null) {
            // Comprobamos primero que no tenemos ya añadido el fragment a nuestra jerarquía
            if (fragmentManager.findFragmentById(R.id.main_content) == null) {
                val fragment = TablesListFragment.newInstance()
                fragmentManager.beginTransaction()
                        .add(R.id.main_content, fragment)
                        .commit()
            }
        }
        addButton.visibility = View.GONE
        addButton?.setOnClickListener {
            startActivity(MenuActivity.intent(this,tablePosition))

        }
    }
    override fun onTableSelected(table: Table?, position: Int) {
        addButton.visibility = View.VISIBLE
       // startActivity(MenuActivity.intent(this,position))
        title ="Mesa ${position}"
        tablePosition = position
        // Comprobamos que en la interfaz tenemos un FrameLayout llamado city_list_fragment
        if (findViewById<View>(R.id.main_content) != null) {
            // Comprobamos primero que no tenemos ya añadido el fragment a nuestra jerarquía
                val fragment = PlatesListFragment.newInstance()
                fragment.list = Tables[tablePosition].platos
                fragmentManager.beginTransaction()
                        .replace(R.id.main_content, fragment)
                        .addToBackStack("TablePlateList")
                        .commit()

        }
    }

    override fun onPlateSelected(plate: Plate?, position: Int) {
        addButton.visibility = View.GONE
        title = "Detalle de Plato"
        MODE = DETAIL_MODE.EDIT
        val fragment = PlateDetailFragment.newInstance(tablePosition,position)
        fragmentManager.beginTransaction()
                .replace(R.id.main_content, fragment)
                .addToBackStack("Detail")
                .commit()
    }
    override fun onBackPressed() {
        if (fragmentManager.backStackEntryCount > 0) {

            fragmentManager.popBackStack()
            var currentFragment = fragmentManager.findFragmentById(R.id.main_content)
            if(currentFragment is PlateDetailFragment){
                addButton.visibility = View.VISIBLE
            }
            if(currentFragment is PlatesListFragment){
                addButton.visibility = View.GONE
            }

        } else {
            super.onBackPressed()
        }
    }
}
