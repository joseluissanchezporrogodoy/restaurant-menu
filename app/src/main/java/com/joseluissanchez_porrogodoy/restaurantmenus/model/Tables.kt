package com.joseluissanchez_porrogodoy.restaurantmenus.model

import com.joseluissanchez_porrogodoy.restaurantmenus.NUMBER_OFF_TABLES
import java.io.Serializable
import java.util.prefs.Preferences

/**
 * Created by joseluissanchez-porrogodoy on 10/11/2017.
 */
object Tables : Serializable {


    private var tables: List<Table> = generateTablesByPreferences()

    val count
        get() = tables.size

    //    fun getCity(index: Int) = cities[index]
    operator fun get(i: Int) = tables[i]

    fun toArray() = tables.toTypedArray()

    fun generateTablesByPreferences():List<Table>{

        var generatedTables = mutableListOf<Table>()
        for(index in 0 until NUMBER_OFF_TABLES){
            generatedTables.add(Table("Mesa ${index}"))
        }
        return generatedTables.toList()
    }
}
