package com.joseluissanchez_porrogodoy.restaurantmenus.model

import java.io.Serializable

/**
 * Created by joseluissanchez-porrogodoy on 11/11/2017.
 */
object CloudPlates :Serializable {

    public var plates = listOf<Plate>()
        set(value) {
            field = value
        }
    operator fun get(i: Int) = plates[i]

}
