package com.joseluissanchez_porrogodoy.restaurantmenus.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.View
import com.joseluissanchez_porrogodoy.restaurantmenus.DETAIL_MODE
import com.joseluissanchez_porrogodoy.restaurantmenus.MODE
import com.joseluissanchez_porrogodoy.restaurantmenus.R
import com.joseluissanchez_porrogodoy.restaurantmenus.adapter.PlatesRecyclerViewAdapter.OnPlateSelectedListener
import com.joseluissanchez_porrogodoy.restaurantmenus.fragment.PlatesListFragment
import com.joseluissanchez_porrogodoy.restaurantmenus.fragment.PlateDetailFragment
import com.joseluissanchez_porrogodoy.restaurantmenus.model.CloudPlates
import com.joseluissanchez_porrogodoy.restaurantmenus.model.Plate

class MenuActivity : AppCompatActivity(), OnPlateSelectedListener {

    var tablePosition: Int = -1
    companion object {
        val EXTRA_TABLE_INDEX = "EXTRA_TABLE_INDEX"
        fun intent(context: Context, tableIndex: Int) : Intent {
            val intent = Intent(context, MenuActivity::class.java)
            intent.putExtra(EXTRA_TABLE_INDEX, tableIndex)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        tablePosition = intent.getIntExtra(EXTRA_TABLE_INDEX, 0)
        if (findViewById<View>(R.id.menu_content) != null) {
            // Comprobamos primero que no tenemos ya añadido el fragment a nuestra jerarquía
            if (fragmentManager.findFragmentById(R.id.menu_content) == null) {
                val fragment = PlatesListFragment.newInstance()
                fragment.list = CloudPlates.plates
                fragmentManager.beginTransaction()
                        .add(R.id.menu_content, fragment)
                        .commit()
            }
        }


    }

    override fun onPlateSelected(plate: Plate?, position: Int) {
        title = "Detalle de Plato"
        MODE = DETAIL_MODE.ADD
        val fragment = PlateDetailFragment.newInstance(tablePosition,position)
        fragmentManager.beginTransaction()
                .replace(R.id.menu_content, fragment)
                .addToBackStack("Detail")
                .commit()
    }

    override fun onBackPressed() {
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()

        } else {
            super.onBackPressed()
        }
    }

}
