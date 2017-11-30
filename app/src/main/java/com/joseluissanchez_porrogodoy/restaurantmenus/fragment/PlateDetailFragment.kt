package com.joseluissanchez_porrogodoy.restaurantmenus.fragment

import android.app.Fragment
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
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
        val EXTRA_PLATE = "PLATE"
        val EXTRA_EDIT_MODE = "EDIT_MODE"
        val EXTRA_TABLE_POSITION = "EXTRA_TABLE_POSITION"
        val EXTRA_PLATE_POSITION = "EXTRA_PLATE_POSITION"



        fun newInstance(tablePosition: Int,plateNumberList: Int,editMode: Boolean): PlateDetailFragment {
            val arguments = Bundle()

            arguments.putBoolean(EXTRA_EDIT_MODE, editMode)
            arguments.putInt(EXTRA_TABLE_POSITION, tablePosition)
            //arguments.putSerializable(EXTRA_PLATE, plate)
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

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        if (inflater != null) {

            if(arguments.getBoolean(EXTRA_EDIT_MODE)){
                plate = Tables.get(arguments.getInt(EXTRA_TABLE_POSITION)).platos!!.get(arguments.getInt(EXTRA_PLATE_POSITION))

            }else{
                plate = CloudPlates.get(arguments.getInt(EXTRA_PLATE_POSITION))
            }



            root = inflater.inflate(R.layout.fragment_detail, container, false)
            val imageView = root.findViewById<ImageView>(R.id.plate_detail_photo)
            val description = root.findViewById<TextView>(R.id.plate_detail_description)
            note = root.findViewById<EditText>(R.id.plate_note)
            val price = root.findViewById<TextView>(R.id.plate_detail_price)
            val name= root.findViewById<TextView>(R.id.plate_name_detail)
            Picasso.with(activity).
                    load(plate.image).
                    fit().
                    into(imageView);
            description.text = plate.description
            price.text = plate.price.toString()
            name.text = plate.name


        }

        return root

    }
}
