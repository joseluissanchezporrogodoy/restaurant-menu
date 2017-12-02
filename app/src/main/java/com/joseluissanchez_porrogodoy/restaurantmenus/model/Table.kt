package com.joseluissanchez_porrogodoy.restaurantmenus.model

import java.io.Serializable

/**
 * Created by joseluissanchez-porrogodoy on 10/11/2017.
 */


data class Table(var name: String, var platos: ArrayList<Plate>) : Serializable {
    constructor(name: String) : this(name, arrayListOf<Plate>())
    override fun toString() = name

    fun calculateCount() : Float {
        var total = 0f
        if (platos.size > 0){
            platos.map { total += it.price }
        }
        return total
    }
}
