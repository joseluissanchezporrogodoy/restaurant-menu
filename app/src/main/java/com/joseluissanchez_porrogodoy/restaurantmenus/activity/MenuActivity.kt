package com.joseluissanchez_porrogodoy.restaurantmenus.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.View
import com.joseluissanchez_porrogodoy.restaurantmenus.R
import com.joseluissanchez_porrogodoy.restaurantmenus.fragment.ContentFragment
import com.joseluissanchez_porrogodoy.restaurantmenus.fragment.MenuFragment

class MenuActivity : AppCompatActivity() {
    companion object {
        val EXTRA_TABLE_INDEX = "EXTRA_TABLE_INDEX"
        fun intent(context: Context, tableIndex: Int) : Intent {
            val intent = Intent(context, MenuActivity::class.java)
            intent.putExtra(EXTRA_TABLE_INDEX, tableIndex)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // Recibimos el índice de la ciudad que queremos mostrar
        val tableIndex = intent.getIntExtra(EXTRA_TABLE_INDEX, 0)
        title ="Mesa ${tableIndex}"
        // Comprobamos que en la interfaz tenemos un FrameLayout llamado city_list_fragment
        if (findViewById<View>(R.id.menu_content) != null) {
            // Comprobamos primero que no tenemos ya añadido el fragment a nuestra jerarquía
            if (fragmentManager.findFragmentById(R.id.menu_content) == null) {
                val fragment = MenuFragment.newInstance()
                fragmentManager.beginTransaction()
                        .add(R.id.menu_content, fragment)
                        .commit()
            }
        }
    }
}
