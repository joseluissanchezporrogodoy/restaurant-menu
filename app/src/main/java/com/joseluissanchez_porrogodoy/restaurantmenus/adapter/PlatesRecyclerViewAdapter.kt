package com.joseluissanchez_porrogodoy.restaurantmenus.adapter
import android.app.ActionBar
import android.content.Context
import android.view.View
import android.support.v7.view.menu.ActionMenuItemView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.joseluissanchez_porrogodoy.restaurantmenus.R
import com.joseluissanchez_porrogodoy.restaurantmenus.model.Plate
import com.squareup.picasso.Picasso
import org.w3c.dom.Text


/**
 * Created by joseluissanchez-porrogodoy on 13/11/2017.
 */


class PlatesRecyclerViewAdapter(val plates: List<Plate>): RecyclerView.Adapter<PlatesRecyclerViewAdapter.PlateListViewHolder>(){

    override fun getItemCount() = plates.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PlateListViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.plate_item, parent, false)
        return PlateListViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlateListViewHolder?, position: Int) {
        holder?.bindPlate(plates[position])
    }

    inner class PlateListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val plateName = itemView.findViewById<TextView>(R.id.plate_name)
        val plateImage = itemView.findViewById<ImageView>(R.id.plate_image)
        val desctiption = itemView.findViewById<TextView>(R.id.plate_description)
        val linear = itemView.findViewById<LinearLayout>(R.id.aller_content)
        fun bindPlate(plate: Plate){
            // Actualizo la vista con el modelo
            // Accedemos al contexto
            val context = itemView.context
            plateName.text = plate.name
            desctiption.text = plate.description
            Picasso.with(context).load("http://c7.alamy.com/comp/B0E0GW/cocido-madrileo-serving-madrid-spain-B0E0GW.jpg").into(plateImage);
            plate.allergens?.let{
               for(i in 0..it.size-1){
                   val lineari = LinearLayout(context)
                   lineari.layoutParams = ViewGroup.LayoutParams(50,50)
                   val view = ImageView(context)
                   view.setImageResource(getAllergenIcon(it.get(i)))
                   lineari.addView(view)
                   linear.addView(lineari)
               }

            }
        }

    }

    fun getAllergenIcon(type: Int): Int{
       val resource =  when (type) {
            0 -> R.drawable.cereals
            1 -> R.drawable.eggs
            2 -> R.drawable.milk
            3 -> R.drawable.peanut
            4 -> R.drawable.pulse
            5 -> R.drawable.shellfish
            else -> R.drawable.cereals
        }
        return resource
    }

}
