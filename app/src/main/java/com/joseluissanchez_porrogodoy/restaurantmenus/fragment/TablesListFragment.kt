package com.joseluissanchez_porrogodoy.restaurantmenus.fragment

import android.app.Activity
import android.app.AlertDialog
import android.app.Fragment
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.ViewSwitcher
import com.joseluissanchez_porrogodoy.restaurantmenus.R
import com.joseluissanchez_porrogodoy.restaurantmenus.model.*
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.android.UI
import org.jetbrains.anko.coroutines.experimental.bg

class TablesListFragment : Fragment() {

    interface OnTableSelectedListener {
        fun onTableSelected(table: Table?, position: Int)
    }
    enum class VIEW_INDEX(val index: Int) {
        LOADING(0),
        LIST(1)
    }
    companion object {
        fun newInstance() = TablesListFragment()
    }

    lateinit var root: View
    lateinit var viewSwitcher: ViewSwitcher
    lateinit var list:ListView
    private var onTableSelectedListener:OnTableSelectedListener? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if (inflater != null) {
            root = inflater.inflate(R.layout.fragment_tables_list, container, false)
            viewSwitcher = root.findViewById(R.id.view_switcher)
            viewSwitcher.setInAnimation(activity, android.R.anim.fade_in)
            viewSwitcher.setOutAnimation(activity, android.R.anim.fade_out)
            getPlateList()
            list = root.findViewById<ListView>(R.id.table_list)
            val adapter = ArrayAdapter<Table>(activity, android.R.layout.simple_list_item_1, Tables.toArray())
            list.adapter = adapter
            list.setOnItemClickListener { parent, view, position, id ->
                // Aviso al listener
                onTableSelectedListener?.onTableSelected(Tables.get(position), position)
            }

        }

        return root
    }

    private fun getPlateList(){
        viewSwitcher.displayedChild = VIEW_INDEX.LOADING.index
        var dowloadMenuInteractor: DownloadMenuInteractor = DownloadMenuInteractorImpl()
        async(UI){
            val newPlateList: Deferred<List<Plate>?> = bg {
                //dowloadPlateList()
                dowloadMenuInteractor.execute()
            }


            val dowloadedPlateList = newPlateList.await()

            if (dowloadedPlateList != null) {
                // Simulamos un retardo
                //Thread.sleep(10000)
                // Toddo ha ido bien,
                CloudPlates.plates = dowloadedPlateList
                viewSwitcher.displayedChild = VIEW_INDEX.LIST.index
            }
            else {
                // Ha habido algún tipo de error, se lo decimos al usuario con un diálogo
                AlertDialog.Builder(activity)
                        .setTitle("Error")
                        .setMessage("No me pude descargar la información del tiempo")
                        .setPositiveButton("Reintentar", { dialog, _ ->
                            dialog.dismiss()
                            getPlateList()
                        })
                        .setNegativeButton("Salir", { _, _ -> activity.finish() })
                        .show()
            }
        }
    }
    @Suppress("OverridingDeprecatedMember", "DEPRECATION")
    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        commonAttach(activity)
    }

    override fun onDetach() {
        super.onDetach()
        onTableSelectedListener = null
    }

    fun commonAttach(listener: Any?) {
        if (listener is OnTableSelectedListener) {
            onTableSelectedListener = listener
        }
    }

}





