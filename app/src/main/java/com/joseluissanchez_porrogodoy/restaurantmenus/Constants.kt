package com.joseluissanchez_porrogodoy.restaurantmenus

/**
 * Created by joseluissanchez-porrogodoy on 01/12/2017.
 */

enum class DETAIL_MODE(val index: Int){
    EDIT(0),
    ADD(1)
}

var MODE: DETAIL_MODE = DETAIL_MODE.EDIT


var JSON_URL = "http://www.mocky.io/v2/5a225ade2f000096045ec5d3"
