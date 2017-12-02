package com.joseluissanchez_porrogodoy.restaurantmenus.fragment

import android.app.Fragment
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.joseluissanchez_porrogodoy.restaurantmenus.DETAIL_MODE
import com.joseluissanchez_porrogodoy.restaurantmenus.MODE
import com.joseluissanchez_porrogodoy.restaurantmenus.R
import com.joseluissanchez_porrogodoy.restaurantmenus.model.CloudPlates
import com.joseluissanchez_porrogodoy.restaurantmenus.model.Plate
import com.joseluissanchez_porrogodoy.restaurantmenus.model.Table
import com.joseluissanchez_porrogodoy.restaurantmenus.model.Tables
import com.squareup.picasso.Picasso

/**
 * Created by joseluissanchez-porrogodoy on 30/11/2017.
 */
class PlateDetailFragment : Fragment() {
    companion object {

        val EXTRA_TABLE_POSITION = "EXTRA_TABLE_POSITION"
        val EXTRA_PLATE_POSITION = "EXTRA_PLATE_POSITION"



        fun newInstance(tablePosition: Int,plateNumberList: Int): PlateDetailFragment {
            val arguments = Bundle()
            arguments.putInt(EXTRA_TABLE_POSITION, tablePosition)
            arguments.putInt(EXTRA_PLATE_POSITION,plateNumberList)
            val fragment = PlateDetailFragment()
            fragment.arguments = arguments
            return fragment
        }
    }
    lateinit var plate: Plate
    lateinit var table: Table
    lateinit var root: View
    lateinit var note: EditText
    var tablePosition: Int = -1
    var platePosition: Int = -1
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        if (inflater != null) {

            platePosition = arguments.getInt(EXTRA_PLATE_POSITION)
            tablePosition = arguments.getInt(EXTRA_TABLE_POSITION)
            when(MODE){
                DETAIL_MODE.ADD->{
                    plate = CloudPlates[platePosition]
                }
                DETAIL_MODE.EDIT->{
                    var plates = Tables[tablePosition].platos
                    plates?.let {
                        plate = it[platePosition]
                    }
                }
            }
            root = inflater.inflate(R.layout.fragment_detail, container, false)
            val imageView = root.findViewById<ImageView>(R.id.plate_detail_photo)
            val description = root.findViewById<TextView>(R.id.plate_detail_description)
            note = root.findViewById<EditText>(R.id.plate_note)

            val price = root.findViewById<TextView>(R.id.plate_detail_price)
            val name= root.findViewById<TextView>(R.id.plate_name_detail)
            root.findViewById<Button>(R.id.add_btn).setOnClickListener { add() }
            root.findViewById<Button>(R.id.cancel_btn).setOnClickListener { cancel() }

            Picasso.with(activity).
                    load(plate.image).
                    fit().
                    into(imageView);
            description.text = plate.description
            price.text = plate.price.toString()
            name.text = plate.name
            note.setText(plate.note)

            //val supportActionBar = (activity as? AppCompatActivity)?.supportActionBar
            //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        return root

    }

    private fun cancel() {
        when(MODE){
            DETAIL_MODE.ADD-> activity.title = "Seleccione un Plato"
            DETAIL_MODE.EDIT-> activity.title = "Mesa ${tablePosition}"
        }

        fragmentManager.popBackStackImmediate()

    }
    private fun add(){
        when(MODE){
            DETAIL_MODE.ADD->{
                activity.title = "Seleccione un Plato"
                plate.updateNote(note.text.toString())
                Tables[tablePosition].platos?.add(plate)

            }
            DETAIL_MODE.EDIT->{
                activity.title = "Mesa ${tablePosition}"
                plate.updateNote(note.text.toString())
                Tables[tablePosition].platos?.get(platePosition)?.updateNote(note.text.toString())
            }
        }
        Toast.makeText(root.context, "Editado/Añadido", Toast.LENGTH_LONG).show()
        fragmentManager.popBackStackImmediate()
    }

}
