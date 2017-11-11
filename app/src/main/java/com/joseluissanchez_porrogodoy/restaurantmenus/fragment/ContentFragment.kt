package com.joseluissanchez_porrogodoy.restaurantmenus.fragment

import android.app.AlertDialog
import android.app.Fragment
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.ViewSwitcher
import com.joseluissanchez_porrogodoy.restaurantmenus.R
import com.joseluissanchez_porrogodoy.restaurantmenus.model.*
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.android.UI
import org.jetbrains.anko.coroutines.experimental.bg

class ContentFragment : Fragment() {

    enum class VIEW_INDEX(val index: Int) {
        LOADING(0),
        LIST(1)
    }
    companion object {

        fun newInstance() = ContentFragment()
    }

    lateinit var root: View
    lateinit var viewSwitcher: ViewSwitcher


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if (inflater != null) {
            root = inflater.inflate(R.layout.fragment_content, container, false)
            viewSwitcher = root.findViewById(R.id.view_switcher)
            viewSwitcher.setInAnimation(activity, android.R.anim.fade_in)
            viewSwitcher.setOutAnimation(activity, android.R.anim.fade_out)
            val list = root.findViewById<ListView>(R.id.table_list)
            Tables.count
            getPlateList()
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
                // Toddo ha ido bien, se lo asigno al atributo forecast
                CloudPlates.plates = dowloadedPlateList
                val a = CloudPlates.plates.count()
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

//    private fun dowloadPlateList(): List<Plate>?{
//        try {
//            val url = URL("http://www.mocky.io/v2/5a05e2b43200002b070e55ca")
//            val jsonString = Scanner(url.openStream(), "UTF-8").useDelimiter("\\A").next()
//            val jsonRoot = JSONObject(jsonString)
//            val list = jsonRoot.getJSONArray("menu")
//
//            val plates = mutableListOf<Plate>()
//
//            for(menuIndex in 0 until  list.length()){
//                val downloadedPlate = list.getJSONObject(menuIndex)
//                val name = downloadedPlate.getString("name")
//                val ingredients = mutableListOf<String>()
//                val ingredientsDW = downloadedPlate.getJSONArray("ingredients")
//                for (i in 0 until ingredientsDW.length()){
//                    ingredients.add(ingredientsDW.getString(i))
//                }
//                val image = downloadedPlate.getString("image").toString()
//                plates.add(Plate(name,ingredients,image))
//            }
//
//            return plates
//        }catch (ex: Exception){
//            ex.printStackTrace()
//        }
//        return null
//    }
}





