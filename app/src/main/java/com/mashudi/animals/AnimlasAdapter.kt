package com.mashudi.animals

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_animals.view.*
import java.time.format.TextStyle

class AnimlasAdapter(var list: List<Animals>,val sharedPreff: SharedPreff) : RecyclerView.Adapter<AnimlasAdapter.ViewHolder>() {
    var onItemClickListener:((Animals) -> Unit)? = null
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(animals: Animals) {
            with(itemView) {
                Glide.with(this).load(animals.img).into(itemImg)
                itemNama.text = animals.nama
                itemInggris.text = animals.inggris
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_animals,parent,false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val animals = list.get(position)
        holder.bind(animals)
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(animals)
//            onItemClickListener?.let { it1 -> it1(bunga) }
        }
        //untuk keperluan setings
        holder.itemView.itemInggris.visibility = if (sharedPreff.inggris) View.VISIBLE else View.GONE
        holder.itemView.itemNama.visibility = if (sharedPreff.nama) View.VISIBLE else View.GONE
        if (sharedPreff.color) {
            holder.itemView.itemInggris.setTextColor(Color.parseColor("#000000"))
            holder.itemView.itemNama.setTextColor(Color.parseColor("#000000"))
        }else{
            holder.itemView.itemInggris.setTextColor(Color.parseColor("#ffffff"))
            holder.itemView.itemNama.setTextColor(Color.parseColor("#ffffff"))
        }
        if (sharedPreff.dark) {
            holder.itemView.itemNama.setTextColor(Color.parseColor("#000000"))
            holder.itemView.itemInggris.setTextColor(Color.parseColor("#000000"))
        }else{
            holder.itemView.itemNama.setTextColor(Color.parseColor("#ffffff"))
            holder.itemView.itemInggris.setTextColor(Color.parseColor("#ffffff"))
        }
    }

}