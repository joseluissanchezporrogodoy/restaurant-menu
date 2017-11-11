package com.joseluissanchez_porrogodoy.restaurantmenus.model

/**
 * Created by joseluissanchez-porrogodoy on 10/11/2017.
 */
data class Plate(val name: String, val ingredients: List<String> ,var image: String, var note: String?){

    // Constructor de conveniencia
    constructor( name: String, ingredients: List<String> , image: String) : this(name, ingredients,image,null)
}
