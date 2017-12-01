package com.joseluissanchez_porrogodoy.restaurantmenus.adapter
import android.view.View
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.joseluissanchez_porrogodoy.restaurantmenus.R
import com.joseluissanchez_porrogodoy.restaurantmenus.model.Plate
import com.squareup.picasso.Picasso


/**
 * Created by joseluissanchez-porrogodoy on 13/11/2017.
 */


class PlatesRecyclerViewAdapter(val plates: List<Plate>, val listener:OnPlateSelectedListener?): RecyclerView.Adapter<PlatesRecyclerViewAdapter.PlateListViewHolder>(){

    private var onPlateSelectedListener: PlatesRecyclerViewAdapter.OnPlateSelectedListener? = null

    override fun getItemCount() = plates.size


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PlateListViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.plate_item, parent, false)
        onPlateSelectedListener = listener
        return PlateListViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlateListViewHolder?, position: Int) {
        holder?.bindPlate(plates[position], position)
    }

    inner class PlateListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val plateName = itemView.findViewById<TextView>(R.id.plate_name)
        val plateImage = itemView.findViewById<ImageView>(R.id.plate_image)
        val desctiption = itemView.findViewById<TextView>(R.id.plate_description)
        val price = itemView.findViewById<TextView>(R.id.price_text_view)
        val linear = itemView.findViewById<LinearLayout>(R.id.aller_content)


        fun bindPlate(plate: Plate, position: Int){
            // Actualizo la vista con el modelo
            // Accedemos al contexto
            val context = itemView.context
            plateName.text = plate.name
            desctiption.text = plate.description
            price.text = "Precio ${plate.price} Euros"
            Picasso.with(context).
                    load(plate.image).
                    fit().
                    into(plateImage);
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
            itemView.setOnClickListener {
                onPlateSelectedListener?.onPlateSelected(plate,position)
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
    interface OnPlateSelectedListener {
        fun onPlateSelected(plate: Plate?, position: Int)
    }
}
