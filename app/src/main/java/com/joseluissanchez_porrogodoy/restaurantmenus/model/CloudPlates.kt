package com.joseluissanchez_porrogodoy.restaurantmenus.model

import com.joseluissanchez_porrogodoy.restaurantmenus.NUMBER_OFF_TABLES
import java.io.Serializable

/**
 * Created by joseluissanchez-porrogodoy on 11/11/2017.
 */
object CloudPlates :Serializable {

    public var plates = arrayListOf<Plate>()
    
    operator fun get(i: Int) = plates[i]


}
