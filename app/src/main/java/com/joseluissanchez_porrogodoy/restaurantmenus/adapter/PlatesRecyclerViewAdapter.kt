package com.joseluissanchez_porrogodoy.restaurantmenus.adapter
import android.content.Context
import android.view.View
import android.support.v7.view.menu.ActionMenuItemView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
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
        val plateAller1 = itemView.findViewById<ImageView>(R.id.ico_image_1)
        val plateAller2 = itemView.findViewById<ImageView>(R.id.ico_image_2)
        val plateAller3 = itemView.findViewById<ImageView>(R.id.ico_image_3)
        val desctiption = itemView.findViewById<TextView>(R.id.plate_description)
        fun bindPlate(plate: Plate){
            // Actualizo la vista con el modelo
            // Accedemos al contexto
            val context = itemView.context
            plateName.text = plate.name
            desctiption.text = plate.description

            Picasso.with(context).load("http://c7.alamy.com/comp/B0E0GW/cocido-madrileo-serving-madrid-spain-B0E0GW.jpg").into(plateImage);
            plateAller1.visibility = View.GONE
            plateAller2.visibility = View.GONE
            plateAller3.visibility = View.GONE
            plate.allergens?.let{
                if(it.size == 0)
                    return
                else if (it.size == 1){
                    plateAller1.visibility = View.VISIBLE
                    plateAller1.setImageResource(getAllergenIcon(it.get(0)))
                }else if (it.size == 2){
                    plateAller1.visibility = View.VISIBLE
                    plateAller1.setImageResource(getAllergenIcon(it.get(0)))
                    plateAller2.visibility = View.VISIBLE
                    plateAller2.setImageResource(getAllergenIcon(it.get(1)))

                }else if (it.size == 3){
                    plateAller1.visibility = View.VISIBLE
                    plateAller1.setImageResource(getAllergenIcon(it.get(0)))
                    plateAller2.visibility = View.VISIBLE
                    plateAller2.setImageResource(getAllergenIcon(it.get(1)))
                    plateAller3.visibility = View.VISIBLE
                    plateAller3.setImageResource(getAllergenIcon(it.get(2)))
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
