package com.joseluissanchez_porrogodoy.restaurantmenus.activity

import android.app.AlertDialog
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
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
    private val CLOUD_PLATE_LIST_TAG = "CloudPlateList"

    private val DETAIL_EDIT_TAG = "DetailEdit"

    private val TABLE_PLATE_LIST_TAG = "TablePlateList"

    private val DETAIL_ADD_TAG = "DetailAdd"

    private val TABLE_LIST_TAG = "TableList"


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

        }else{
            tableMode = false
            title = resources.getString(R.string.selec_table)
            addButton.visibility = View.GONE
        }

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
                        .replace(R.id.main_content, fragment, TABLE_LIST_TAG)
                        .commit()
            }
        }

        if(tableMode){
            tablePosition= 0
            MODE = DETAIL_MODE.EDIT
            setButtonVisivility()
            val fragment = PlatesListFragment.newInstance()
            fragment.list = Tables[tablePosition].platos
            title = resources.getString(R.string.table)+" ${tablePosition}"
            fragmentManager.beginTransaction()
                    .replace(R.id.extra_content, fragment, TABLE_PLATE_LIST_TAG)
                    .commit()

        }


        setAddButton()

    }

    private fun setAddButton() {
        addButton.setOnClickListener {
            if (!tableMode) {
                title = resources.getString(R.string.table)+" ${tablePosition}/"+resources.getString(R.string.selec_plate)
                addButton.visibility = View.GONE
                if (fragmentManager.findFragmentById(R.id.main_content) != null) {
                    val fragment = PlatesListFragment.newInstance()
                    fragment.list = CloudPlates.plates
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_content, fragment, CLOUD_PLATE_LIST_TAG)
                            .addToBackStack(CLOUD_PLATE_LIST_TAG)
                            .commit()
                }
            } else {
                title = resources.getString(R.string.table)+" ${tablePosition}/"+resources.getString(R.string.selec_plate)
                addButton.visibility = View.GONE
                if (fragmentManager.findFragmentById(R.id.main_content) != null) {
                    val fragment = PlatesListFragment.newInstance()
                    fragment.list = CloudPlates.plates
                    fragmentManager.beginTransaction()
                            .replace(R.id.extra_content, fragment, CLOUD_PLATE_LIST_TAG)
                            .addToBackStack(CLOUD_PLATE_LIST_TAG)
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
        var message: String
        val title: String
        when(tablePosition){
            -1 -> {
                title = resources.getString(R.string.selec_table)
                message = ""
            }
            else->{
                val total = Tables[tablePosition].calculateCount()
                title =   resources.getString(R.string.total_count)+" ${tablePosition}"
                message = resources.getString(R.string.total_message, total.toString())

                when (total) {
                    0f -> message = resources.getString(R.string.total_empty)
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
           title = resources.getString(R.string.table)+" ${position}"
           tablePosition = position
           if (findViewById<View>(R.id.main_content) != null) {
               val fragment = PlatesListFragment.newInstance()
               fragment.list = Tables[tablePosition].platos
               fragmentManager.beginTransaction()
                       .replace(R.id.main_content, fragment, TABLE_PLATE_LIST_TAG)
                       .addToBackStack(TABLE_PLATE_LIST_TAG)
                       .commit()

           }
       }else{
           title = resources.getString(R.string.table)+" ${position}"
           tablePosition = position
           setButtonVisivility()
           val fragment = PlatesListFragment.newInstance()
           if(tablePosition == -1)
               tablePosition = 0

           title =resources.getString(R.string.table)+" ${tablePosition}"
           fragment.list = Tables[tablePosition].platos
           fragmentManager.beginTransaction()
                   .replace(R.id.extra_content, fragment, TABLE_PLATE_LIST_TAG)
                   .commit()
       }

    }

    override fun onPlateSelected(plate: Plate?, position: Int) {
        platePosition = position
        if(!tableMode) {
            addButton.visibility = View.GONE
            title = resources.getString(R.string.plate_detail)
            when (fragmentManager.backStackEntryCount) {
                1 ->{
                    MODE = DETAIL_MODE.EDIT
                    val fragment = PlateDetailFragment.newInstance(tablePosition, platePosition)
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_content, fragment, DETAIL_EDIT_TAG)
                            .addToBackStack(DETAIL_EDIT_TAG)
                            .commit()
                }
                2 ->{
                    MODE = DETAIL_MODE.ADD
                    val fragment = PlateDetailFragment.newInstance(tablePosition, platePosition)
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_content, fragment, DETAIL_ADD_TAG)
                            .addToBackStack(DETAIL_ADD_TAG)
                            .commit()
                }
            }

        }else{

            addButton.visibility = View.GONE
            val currenFragment = fragmentManager.findFragmentById(R.id.extra_content)
            when (currenFragment.tag) {
                TABLE_PLATE_LIST_TAG ->{
                    MODE = DETAIL_MODE.EDIT
                    title = resources.getString(R.string.table)+" ${tablePosition}/"+resources.getString(R.string.plate_detail)
                    val fragment = PlateDetailFragment.newInstance(tablePosition, platePosition)
                    fragmentManager.beginTransaction()
                            .replace(R.id.extra_content, fragment, DETAIL_EDIT_TAG)
                            .addToBackStack(DETAIL_EDIT_TAG)
                            .commit()
                }
                CLOUD_PLATE_LIST_TAG ->{
                    MODE = DETAIL_MODE.ADD
                    title = resources.getString(R.string.table)+" ${tablePosition}/"+resources.getString(R.string.selec_plate)+"/"+resources.getString(R.string.plate_detail)
                    val fragment = PlateDetailFragment.newInstance(tablePosition, platePosition)
                    fragmentManager.beginTransaction()
                            .replace(R.id.extra_content, fragment, DETAIL_ADD_TAG)
                            .addToBackStack(DETAIL_ADD_TAG)
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

                    CLOUD_PLATE_LIST_TAG ->{
                        title =resources.getString(R.string.table)+" ${tablePosition}"
                        addButton.visibility = View.VISIBLE
                    }
                    DETAIL_EDIT_TAG ->{
                        title = resources.getString(R.string.table)+" ${tablePosition}/"+resources.getString(R.string.selec_plate)
                        addButton.visibility = View.GONE
                    }
                    TABLE_PLATE_LIST_TAG ->{
                        tablePosition = -1
                        title = resources.getString(R.string.selec_table)
                        addButton.visibility = View.GONE
                    }
                    DETAIL_ADD_TAG ->{
                        title =resources.getString(R.string.table)+" ${tablePosition}"
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
                    title = resources.getString(R.string.table)+"${tablePosition}/"+resources.getString(R.string.selec_plate)
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
    fun showMessage(){
        Snackbar.make(findViewById(android.R.id.content), "Esto es otra prueba", Snackbar.LENGTH_LONG)
                .setActionTextColor(Color.CYAN)
                .setAction("Acción") {

                }
                .show()
    }

}
