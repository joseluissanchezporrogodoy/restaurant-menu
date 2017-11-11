package com.joseluissanchez_porrogodoy.restaurantmenus.model

import org.json.JSONObject
import java.net.URL
import java.util.*

/**
 * Created by joseluissanchez-porrogodoy on 11/11/2017.
 */

class DownloadMenuInteractorImpl: DownloadMenuInteractor{
    override fun execute(): List<Plate>? {
        try {
            val url = URL("http://www.mocky.io/v2/5a05e2b43200002b070e55ca")
            val jsonString = Scanner(url.openStream(), "UTF-8").useDelimiter("\\A").next()
            val jsonRoot = JSONObject(jsonString)
            val list = jsonRoot.getJSONArray("menu")

            val plates = mutableListOf<Plate>()

            for(menuIndex in 0 until  list.length()){
                val downloadedPlate = list.getJSONObject(menuIndex)
                val name = downloadedPlate.getString("name")
                val ingredients = mutableListOf<String>()
                val ingredientsDW = downloadedPlate.getJSONArray("ingredients")
                for (i in 0 until ingredientsDW.length()){
                    ingredients.add(ingredientsDW.getString(i))
                }
                val image = downloadedPlate.getString("image").toString()
                plates.add(Plate(name,ingredients,image))
            }

            return plates.toList()
        }catch (ex: Exception){
            ex.printStackTrace()
            return null
        }
    }
}
