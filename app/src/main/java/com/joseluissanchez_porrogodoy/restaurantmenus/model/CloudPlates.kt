package com.joseluissanchez_porrogodoy.restaurantmenus.model

import com.joseluissanchez_porrogodoy.restaurantmenus.NUMBER_OFF_TABLES
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

    fun getPlatesNameList():List<String>{
        var generated = mutableListOf<String>()
        for(index in 0.. this.plates.size-1){
            generated.add(this.plates.get(index).name)
        }
        return generated.toList()
    }
}
