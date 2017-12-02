package com.joseluissanchez_porrogodoy.restaurantmenus.activity

import android.app.AlertDialog
import android.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.Menu
import android.view.MenuItem
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
        title = "Seleccione una mesa"

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
            title = "Seleccione un Plato"
            addButton.visibility = View.GONE
            if (fragmentManager.findFragmentById(R.id.main_content) != null) {
                val fragment = PlatesListFragment.newInstance()
                fragment.list = CloudPlates.plates
                fragmentManager.beginTransaction()
                        .replace(R.id.main_content, fragment)
                        .addToBackStack("CloudPlateList")
                        .commit()
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        getMenuInflater()?.inflate(R.menu.settings, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.table_count) {
            showCount()
        }

        return true
    }
    private fun showCount() {
        var message = ""
        var title =""
        when(tablePosition){
            -1 -> {
                title = "Seleccione una mesa"
                message = ""
            }
            else->{
                var total = Tables[tablePosition].calculateCount()
                title = "Cuenta de la mesa ${tablePosition}"
                message = "Total a pagar: ${total} euros"

                when (total) {
                    0f -> message = "No hay platos añadidos."
                }
            }

        }

        AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(getString(android.R.string.ok), { dialog, _ ->
                    dialog.dismiss()
                })
                .show()
    }

    override fun onTableSelected(table: Table?, position: Int) {
        addButton.visibility = View.VISIBLE
       // startActivity(MenuActivity.intent(this,position))
        title ="Mesa ${position}"
        tablePosition = position
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
        when(fragmentManager.backStackEntryCount){
            1-> MODE = DETAIL_MODE.EDIT
            2->MODE = DETAIL_MODE.ADD
        }
        val fragment = PlateDetailFragment.newInstance(tablePosition,position)
        fragmentManager.beginTransaction()
                .replace(R.id.main_content, fragment)
                .addToBackStack("Detail")
                .commit()
    }
    override fun onBackPressed() {
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
            val index = fragmentManager.backStackEntryCount - 1
            val currentFragment = fragmentManager.findFragmentById(R.id.main_content)
            when(currentFragment){
                is PlateDetailFragment->when(index){
                        2->{
                            title = "Seleccione un Plato"
                            addButton.visibility = View.GONE
                        }
                        1->{
                            title ="Mesa ${tablePosition}"
                            addButton.visibility = View.VISIBLE
                        }
                    }

                is PlatesListFragment->when(index){
                    1-> {
                        title ="Mesa ${tablePosition}"
                        addButton.visibility = View.VISIBLE
                    }
                    0-> {
                        tablePosition = -1
                        title = "Seleccione una mesa"
                        addButton.visibility = View.GONE
                    }
                }
            }

        } else {
            super.onBackPressed()
        }
    }

    fun setButtonVisivility(){
        addButton.visibility = View.VISIBLE
    }

}
