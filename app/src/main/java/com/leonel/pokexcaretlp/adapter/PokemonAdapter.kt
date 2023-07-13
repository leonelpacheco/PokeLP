package com.leonel.pokexcaretlp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.leonel.pokexcaretlp.R
import com.leonel.pokexcaretlp.model.PokemonResult
import com.leonel.pokexcaretlp.utils.Constant
import com.squareup.picasso.Picasso

class PokemonAdapter (private val mList: List<PokemonResult>): RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_pokemon, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]
        val idPokemon = ItemsViewModel.url.split("/")
        holder.title.text = ItemsViewModel.name
        Picasso.get().load(Constant.URL_IMAGES + idPokemon[idPokemon.size-2] +".png").error(R.drawable.ic_image).into(holder.imgpokemon)
        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, ItemsViewModel)
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }
    interface OnClickListener {
        fun onClick(position: Int, model: PokemonResult)
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val title: TextView = itemView.findViewById(R.id.txt_titlepok)
        val imgpokemon: ImageView = itemView.findViewById(R.id.img_pokemon)
    }
}