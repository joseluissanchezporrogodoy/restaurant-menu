package com.joseluissanchez_porrogodoy.restaurantmenus.model

import org.json.JSONObject
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by joseluissanchez-porrogodoy on 11/11/2017.
 */

class DownloadMenuInteractorImpl: DownloadMenuInteractor{
    override fun execute(): ArrayList<Plate>? {
        if(CloudPlates.plates.count() == 0) {
            try {
                val url = URL("http://www.mocky.io/v2/5a225ade2f000096045ec5d3")
                val jsonString = Scanner(url.openStream(), "UTF-8").useDelimiter("\\A").next()
                val jsonRoot = JSONObject(jsonString)
                val list = jsonRoot.getJSONArray("menu")

                val plates = arrayListOf<Plate>()

                for (menuIndex in 0 until list.length()) {
                    val downloadedPlate = list.getJSONObject(menuIndex)
                    val name = downloadedPlate.getString("name")
                    val allergens = mutableListOf<Int>()
                    val allergensDW = downloadedPlate.getJSONArray("allergens")
                    for (i in 0 until allergensDW.length()) {
                        allergens.add(allergensDW.getString(i).toInt())
                    }
                    val price = downloadedPlate.getDouble("price").toFloat()
                    val image = downloadedPlate.getString("image").toString()
                    val description = downloadedPlate.getString("description").toString()
                    plates.add(Plate(name, allergens, image, description, price))
                }

                return plates
            } catch (ex: Exception) {
                ex.printStackTrace()
                return null
            }
        }else{
            return CloudPlates.plates
        }

    }
}
