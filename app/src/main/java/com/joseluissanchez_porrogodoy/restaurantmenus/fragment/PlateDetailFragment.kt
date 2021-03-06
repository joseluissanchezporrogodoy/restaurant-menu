package com.joseluissanchez_porrogodoy.restaurantmenus.fragment

import android.app.Fragment
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.joseluissanchez_porrogodoy.restaurantmenus.DETAIL_MODE
import com.joseluissanchez_porrogodoy.restaurantmenus.MODE
import com.joseluissanchez_porrogodoy.restaurantmenus.R
import com.joseluissanchez_porrogodoy.restaurantmenus.activity.ContentActivity
import com.joseluissanchez_porrogodoy.restaurantmenus.model.CloudPlates
import com.joseluissanchez_porrogodoy.restaurantmenus.model.Plate
import com.joseluissanchez_porrogodoy.restaurantmenus.model.Table
import com.joseluissanchez_porrogodoy.restaurantmenus.model.Tables
import com.squareup.picasso.Picasso
import android.support.design.widget.Snackbar



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
    lateinit var root: View

    lateinit var name: TextView
    lateinit var imageView: ImageView
    lateinit var description: TextView
    lateinit var price: TextView
    lateinit var note: EditText



    var tablePosition: Int = -1
    var platePosition: Int = -1
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        if (inflater != null) {

            platePosition = arguments.getInt(EXTRA_PLATE_POSITION)
            tablePosition = arguments.getInt(EXTRA_TABLE_POSITION)
            setDetailViews(inflater, container)

            when(MODE){
                DETAIL_MODE.ADD->{
                    plate = CloudPlates[platePosition]
                    root.findViewById<Button>(R.id.add_btn).setText(resources.getString(R.string.add))
                    root.findViewById<Button>(R.id.cancel_btn).setText(resources.getString(R.string.cancel))
                    root.findViewById<Button>(R.id.cancel_btn).setOnClickListener { cancel() }
                }
                DETAIL_MODE.EDIT->{
                    plate = Tables[tablePosition].platos[platePosition]
                    root.findViewById<Button>(R.id.add_btn).setText(resources.getString(R.string.edit))
                    root.findViewById<Button>(R.id.cancel_btn).setText(resources.getString(R.string.delete))
                    root.findViewById<Button>(R.id.cancel_btn).setOnClickListener { delete()}
                }
            }
            root.findViewById<Button>(R.id.add_btn).setOnClickListener { add() }


            Picasso.with(activity).
                    load(plate.image).
                    fit().
                    into(imageView);
            description.text = plate.description
            price.text = plate.price.toString()
            name.text = plate.name
            note.setText(plate.note)

        }

        return root

    }

    private fun setDetailViews(inflater: LayoutInflater, container: ViewGroup?) {
        root = inflater.inflate(R.layout.fragment_detail, container, false)
        name = root.findViewById<TextView>(R.id.plate_name_detail)
        imageView = root.findViewById<ImageView>(R.id.plate_detail_photo)
        description = root.findViewById<TextView>(R.id.plate_detail_description)
        price = root.findViewById<TextView>(R.id.plate_detail_price)
        note = root.findViewById<EditText>(R.id.plate_note)
    }

    private fun cancel() {
        when(MODE){
            DETAIL_MODE.ADD-> activity.title = resources.getString(R.string.table)+" ${tablePosition}/"+resources.getString(R.string.selec_plate)
            DETAIL_MODE.EDIT-> activity.title = resources.getString(R.string.table)+" ${tablePosition}"
        }

        fragmentManager.popBackStackImmediate()
    }
    private fun delete(){
        Tables[tablePosition].platos.removeAt(platePosition)
        activity.title = resources.getString(R.string.table)+" ${tablePosition}"
        ( activity as ContentActivity).setButtonVisivility()
        fragmentManager.popBackStackImmediate()
        Toast.makeText(root.context, resources.getString(R.string.deleted), Toast.LENGTH_LONG).show()


    }
    private fun add(){
        when(MODE) {
            DETAIL_MODE.ADD -> {
                (activity as ContentActivity).setTitle(resources.getString(R.string.table) + " ${tablePosition}/" + resources.getString(R.string.selec_plate))
                var plateTosave = plate.copy()
                plateTosave.updateNote(note.text.toString())
                Tables[tablePosition].platos.add(plateTosave)
                Toast.makeText(root.context, resources.getString(R.string.added), Toast.LENGTH_LONG).show()
            }
            DETAIL_MODE.EDIT -> {
                (activity as ContentActivity).setTitle(resources.getString(R.string.table) + " ${tablePosition}")
                (activity as ContentActivity).setButtonVisivility()
                plate.updateNote(note.text.toString())
                Tables[tablePosition].platos.get(platePosition).updateNote(note.text.toString())
                Toast.makeText(root.context, resources.getString(R.string.edited), Toast.LENGTH_LONG).show()
            }
        }

        fragmentManager.popBackStackImmediate()
    }


}
