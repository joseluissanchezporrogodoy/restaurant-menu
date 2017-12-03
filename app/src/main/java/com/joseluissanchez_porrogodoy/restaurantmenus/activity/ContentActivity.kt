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
    val EXTRA_TITLE = "EXTRA_TITLE"
    val EXTRA_BUTTON_VISIBILITY = "EXTRA_BUTTON_VISIBILITY"
    val EXTRA_FRAGMENT_TAG = "EXTRA_FRAGMENT_TAG"

    val addButton: FloatingActionButton by lazy { findViewById<FloatingActionButton>(R.id.add_plate_button) }
    var tablePosition: Int = -1
    var platePosition: Int = -1
    var fragmentTag:String = ""
    var tableMode = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        if(findViewById<View>(R.id.extra_content)!= null){
            tableMode = true
            addButton.visibility = View.VISIBLE
            //initTableMode()
        }else{
            tableMode = false
            title = "Seleccione una mesa"
            addButton.visibility = View.GONE
        }
        /// Solo recupero los datos no me preocupo de otra cosa
        if (savedInstanceState != null){
            tablePosition = savedInstanceState.getInt(PlateDetailFragment.EXTRA_TABLE_POSITION)
            platePosition = savedInstanceState.getInt(PlateDetailFragment.EXTRA_PLATE_POSITION)
            fragmentTag = savedInstanceState.getString(EXTRA_FRAGMENT_TAG)
        }


        if (findViewById<View>(R.id.main_content) != null) {
            tablePosition = -1
            if (fragmentManager.findFragmentById(R.id.main_content) == null || tableMode) {
                val fragment = TablesListFragment.newInstance()
                fragmentManager.beginTransaction()
                        .replace(R.id.main_content, fragment,"TableList")
                        .commit()
            }
        }

        if(tableMode){
            tablePosition= 0
            MODE = DETAIL_MODE.EDIT
            setButtonVisivility()
            val fragment = PlatesListFragment.newInstance()
            fragment.list = Tables[tablePosition].platos
            title = "Mesa ${tablePosition}"
            fragmentManager.beginTransaction()
                    .replace(R.id.extra_content, fragment,"TablePlateList")
                    .commit()

        }


        setAddButton()

    }

    private fun setAddButton() {
        addButton?.setOnClickListener {
            if (!tableMode) {
                title = "Mesa ${tablePosition}/Seleccione un plato"
                addButton.visibility = View.GONE
                if (fragmentManager.findFragmentById(R.id.main_content) != null) {
                    val fragment = PlatesListFragment.newInstance()
                    fragment.list = CloudPlates.plates
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_content, fragment, "CloudPlateList")
                            .addToBackStack("CloudPlateList")
                            .commit()
                }
            } else {
                title = "Mesa ${tablePosition}/Seleccione un plato"
                addButton.visibility = View.GONE
                if (fragmentManager.findFragmentById(R.id.main_content) != null) {
                    val fragment = PlatesListFragment.newInstance()
                    fragment.list = CloudPlates.plates
                    fragmentManager.beginTransaction()
                            .replace(R.id.extra_content, fragment, "CloudPlateList")
                            .addToBackStack("CloudPlateList")
                            .commit()
                }
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
       if(!tableMode) {
           addButton.visibility = View.VISIBLE
           // startActivity(MenuActivity.intent(this,position))
           title = "Mesa ${position}"
           tablePosition = position
           if (findViewById<View>(R.id.main_content) != null) {
               // Comprobamos primero que no tenemos ya añadido el fragment a nuestra jerarquía
               val fragment = PlatesListFragment.newInstance()
               fragment.list = Tables[tablePosition].platos
               fragmentManager.beginTransaction()
                       .replace(R.id.main_content, fragment,"TablePlateList")
                       .addToBackStack("TablePlateList")
                       .commit()

           }
       }else{
           title = "Mesa ${position}"
           tablePosition = position
           setButtonVisivility()
           val fragment = PlatesListFragment.newInstance()
           if(tablePosition == -1)
               tablePosition = 0

           title ="Mesa ${tablePosition}"
           fragment.list = Tables[tablePosition].platos
           fragmentManager.beginTransaction()
                   .replace(R.id.extra_content, fragment,"TablePlateList")
                   .commit()
       }

    }

    override fun onPlateSelected(plate: Plate?, position: Int) {
        platePosition = position
        if(!tableMode) {
            addButton.visibility = View.GONE
            title = "Detalle de Plato"
            when (fragmentManager.backStackEntryCount) {
                1 ->{
                    MODE = DETAIL_MODE.EDIT
                    val fragment = PlateDetailFragment.newInstance(tablePosition, platePosition)
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_content, fragment,"DetailEdit")
                            .addToBackStack("DetailEdit")
                            .commit()
                }
                2 ->{
                    MODE = DETAIL_MODE.ADD
                    val fragment = PlateDetailFragment.newInstance(tablePosition, platePosition)
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_content, fragment,"DetailAdd")
                            .addToBackStack("DetailAdd")
                            .commit()
                }
            }

        }else{

            addButton.visibility = View.GONE
            val currenFragment = fragmentManager.findFragmentById(R.id.extra_content)
            when (currenFragment.tag) {
                "TablePlateList" ->{
                    MODE = DETAIL_MODE.EDIT
                    title = "Mesa ${tablePosition}/Detalle de Plato"
                    val fragment = PlateDetailFragment.newInstance(tablePosition, platePosition)
                    fragmentManager.beginTransaction()
                            .replace(R.id.extra_content, fragment,"DetailEdit")
                            .addToBackStack("DetailEdit")
                            .commit()
                }
                "CloudPlateList" ->{
                    MODE = DETAIL_MODE.ADD
                    title = "Mesa ${tablePosition}/Seleccione un plato/Detalle de Plato"
                    val fragment = PlateDetailFragment.newInstance(tablePosition, platePosition)
                    fragmentManager.beginTransaction()
                            .replace(R.id.extra_content, fragment,"DetailAdd")
                            .addToBackStack("DetailAdd")
                            .commit()
                }
            }


        }
    }
    override fun onBackPressed() {
        if(!tableMode){
            if (fragmentManager.backStackEntryCount > 0) {
                fragmentManager.popBackStack()
                val currentFragment = fragmentManager.findFragmentById(R.id.main_content)
                when(currentFragment.tag){

                    "CloudPlateList" ->{
                        title ="Mesa ${tablePosition}"
                        addButton.visibility = View.VISIBLE
                    }
                    "DetailEdit" ->{
                        title = "Mesa ${tablePosition}/Seleccione un plato"
                        addButton.visibility = View.GONE
                    }
                    "TablePlateList" ->{
                        tablePosition = -1
                        title = "Seleccione una mesa"
                        addButton.visibility = View.GONE
                    }
                    "DetailAdd" ->{
                        title ="Mesa ${tablePosition}"
                        addButton.visibility = View.VISIBLE
                    }
                }

            } else {
                super.onBackPressed()
            }
        }
    }

    fun setButtonVisivility(){
        addButton.visibility = View.VISIBLE
    }
    fun setTitle(newTitle: String){
        if(!tableMode){
            title = newTitle
        }else{
            when(MODE){
                DETAIL_MODE.ADD->{
                    title = "Mesa ${tablePosition}/Seleccione un plato"
                }
                DETAIL_MODE.EDIT->{
                    title = newTitle
                }
            }
        }

    }
    private fun isAddButtonVisible():Boolean{
        var vis = false
        when(addButton.visibility){
            View.VISIBLE-> vis =  true
            View.GONE -> vis =  false
        }
        return vis
    }
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putSerializable(PlateDetailFragment.EXTRA_TABLE_POSITION,tablePosition)
        outState?.putSerializable(PlateDetailFragment.EXTRA_PLATE_POSITION,platePosition)
        outState?.putSerializable(EXTRA_TITLE,title.toString())
        outState?.putSerializable(EXTRA_BUTTON_VISIBILITY,isAddButtonVisible())
        outState?.putSerializable(EXTRA_FRAGMENT_TAG,getPreviousFragmentTag())

    }

    private fun getPreviousFragmentTag():String{
        when(tableMode){
            true ->{
                val currentFragment = fragmentManager.findFragmentById(R.id.extra_content)

                return currentFragment.tag
            }
            false ->{
                val currentFragment = fragmentManager.findFragmentById(R.id.main_content)

                return currentFragment.tag
            }

        }

    }

}
