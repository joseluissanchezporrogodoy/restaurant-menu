package com.joseluissanchez_porrogodoy.restaurantmenus.model

/**
 * Created by joseluissanchez-porrogodoy on 10/11/2017.
 */
data class Plate(val name: String, val ingredients: List<String>?,var allergens: List<Int>? ,var image: String?,var description: String?, var note: String?){

    // Constructor de conveniencia
    constructor( name: String, ingredients: List<String>?,allergens: List<Int>? , image: String?,description: String? ) : this(name, ingredients,allergens,image,description,null)
    constructor( name: String) : this(name, null,null,null,null,null)
}
