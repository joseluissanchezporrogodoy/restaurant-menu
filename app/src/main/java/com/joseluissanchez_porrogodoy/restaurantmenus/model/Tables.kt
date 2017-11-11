package com.joseluissanchez_porrogodoy.restaurantmenus.model

import com.joseluissanchez_porrogodoy.restaurantmenus.NUMBER_OFF_TABLES
import java.io.Serializable
import java.util.prefs.Preferences

/**
 * Created by joseluissanchez-porrogodoy on 10/11/2017.
 */
object Tables : Serializable {


    private var tables: List<Table> = listOf(
            Table("Mesa 1"),
            Table("Mesa 2"),
            Table("Mesa 3"),
            Table("Mesa 4"),
            Table("Mesa 5"),
            Table("Mesa 6"),
            Table("Mesa 7"),
            Table("Mesa 8"),
            Table("Mesa 9"),
            Table("Mesa 10")
    )

    val count
        get() = tables.size

    //    fun getCity(index: Int) = cities[index]
    operator fun get(i: Int) = tables[i]

    fun toArray() = tables.toTypedArray()
}
