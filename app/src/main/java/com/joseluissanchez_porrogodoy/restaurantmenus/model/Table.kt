package com.joseluissanchez_porrogodoy.restaurantmenus.model

import java.io.Serializable

/**
 * Created by joseluissanchez-porrogodoy on 10/11/2017.
 */


data class Table(var name: String, var platos: MutableList<Plate>) : Serializable {
    constructor(name: String) : this(name, mutableListOf<Plate>())
    override fun toString() = name
}
