package com.joseluissanchez_porrogodoy.restaurantmenus.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ListView
import com.joseluissanchez_porrogodoy.restaurantmenus.DETAIL_MODE
import com.joseluissanchez_porrogodoy.restaurantmenus.MODE
import com.joseluissanchez_porrogodoy.restaurantmenus.R
import com.joseluissanchez_porrogodoy.restaurantmenus.adapter.PlatesRecyclerViewAdapter
import com.joseluissanchez_porrogodoy.restaurantmenus.adapter.PlatesRecyclerViewAdapter.OnPlateSelectedListener
import com.joseluissanchez_porrogodoy.restaurantmenus.fragment.ContentFragment
import com.joseluissanchez_porrogodoy.restaurantmenus.fragment.MenuFragment
import com.joseluissanchez_porrogodoy.restaurantmenus.fragment.PlateDetailFragment
import com.joseluissanchez_porrogodoy.restaurantmenus.model.CloudPlates
import com.joseluissanchez_porrogodoy.restaurantmenus.model.Plate
import com.joseluissanchez_porrogodoy.restaurantmenus.model.Table
import com.joseluissanchez_porrogodoy.restaurantmenus.model.Tables

class MenuActivity : AppCompatActivity(), OnPlateSelectedListener {


    companion object {
        val EXTRA_TABLE_INDEX = "EXTRA_TABLE_INDEX"
        fun intent(context: Context, tableIndex: Int) : Intent {
            val intent = Intent(context, MenuActivity::class.java)
            intent.putExtra(EXTRA_TABLE_INDEX, tableIndex)
            return intent
        }
    }
    val addButton: FloatingActionButton by lazy { findViewById<FloatingActionButton>(R.id.add_plate_button) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // Recibimos el índice de la ciudad que queremos mostrar
        val tableIndex = intent.getIntExtra(EXTRA_TABLE_INDEX, 0)
        title ="Mesa ${tableIndex}"
        // Comprobamos que en la interfaz tenemos un FrameLayout llamado city_list_fragment
        if (findViewById<View>(R.id.menu_content) != null) {
            // Comprobamos primero que no tenemos ya añadido el fragment a nuestra jerarquía
            if (fragmentManager.findFragmentById(R.id.menu_content) == null) {
                val fragment = MenuFragment.newInstance()
                val list: List<Plate> = ArrayList()
                fragment.list = list
                fragmentManager.beginTransaction()
                        .add(R.id.menu_content, fragment)
                        .commit()
            }
        }
        addButton?.setOnClickListener {
            addButton.visibility = View.GONE
            title = "Seleccione platos"
            val fragment = MenuFragment.newInstance()
            fragment.list = CloudPlates.plates
            fragmentManager.beginTransaction()
                    .replace(R.id.menu_content, fragment)
                    .commit()
        }
    }

    override fun onPlateSelected(plate: Plate?, position: Int) {

        addButton.visibility = View.GONE
        title = "Detalle de Plato"
        MODE = DETAIL_MODE.ADD
        val fragment = PlateDetailFragment.newInstance(0,0)
        fragmentManager.beginTransaction()
                .replace(R.id.menu_content, fragment)
                .commit()
    }
}
