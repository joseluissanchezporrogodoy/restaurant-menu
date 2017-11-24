package com.joseluissanchez_porrogodoy.restaurantmenus.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.joseluissanchez_porrogodoy.restaurantmenus.fragment.ContentFragment
import com.joseluissanchez_porrogodoy.restaurantmenus.R
import com.joseluissanchez_porrogodoy.restaurantmenus.model.Table

class ContentActivity : AppCompatActivity(), ContentFragment.OnTableSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)
        // Comprobamos que en la interfaz tenemos un FrameLayout llamado city_list_fragment
        if (findViewById<View>(R.id.main_content) != null) {
            // Comprobamos primero que no tenemos ya añadido el fragment a nuestra jerarquía
            if (fragmentManager.findFragmentById(R.id.main_content) == null) {
                val fragment = ContentFragment.newInstance()
                fragmentManager.beginTransaction()
                        .add(R.id.main_content, fragment)
                        .commit()
            }
        }
    }
    override fun onTableSelected(table: Table?, position: Int) {

        startActivity(MenuActivity.intent(this,position))
    }
}
