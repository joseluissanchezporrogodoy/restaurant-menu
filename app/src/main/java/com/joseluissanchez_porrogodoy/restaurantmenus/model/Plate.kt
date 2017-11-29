package com.joseluissanchez_porrogodoy.restaurantmenus.model

import java.io.Serializable

/**
 * Created by joseluissanchez-porrogodoy on 10/11/2017.
 */
data class Plate(val name: String,
                 var allergens: List<Int>? ,
                 var image: String?,
                 var description: String?,
                 var note: String?,
                 var price:Float) : Serializable{

    // Constructor de conveniencia
    constructor( name: String,
                 allergens: List<Int>?,
                 image: String?,
                 description: String? ,price: Float) : this(name,allergens,image,description,null,price)

}
