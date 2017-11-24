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
            val url = URL("http://www.mocky.io/v2/5a18770f2c00005038596e02")
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
                val allergens = mutableListOf<Int>()
                val allergensDW = downloadedPlate.getJSONArray("allergens")
                for (i in 0 until allergensDW.length()){
                    allergens.add(allergensDW.getString(i).toInt())
                }
                val image = downloadedPlate.getString("image").toString()
                val description = downloadedPlate.getString("description").toString()
                plates.add(Plate(name,ingredients,allergens,image,description))
            }

            return plates.toList()
        }catch (ex: Exception){
            ex.printStackTrace()
            return null
        }
    }
}
