package com.by_jogehrt.mygot

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.by_jogehrt.mygot.databinding.ListElementBinding

class InfoAdapter(private val infoNodes : ArrayList<InfoNode>, private val onInfoClick :(inf : InfoNode, index : Int) -> Unit) : RecyclerView.Adapter<InfoAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(ListElementBinding.inflate(LayoutInflater.from(parent.context)))
    override fun getItemCount(): Int = infoNodes.size
    class ViewHolder(private val binding: ListElementBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(inf : InfoNode){
            binding.tvNombre.text = inf.f_name
            binding.tvApellido.text = inf.l_name
            //binding.ivProfile.src = inf.image
        }
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(infoNodes[position])
        holder.itemView.setOnClickListener{
            onInfoClick(infoNodes[position], position)
        }


    }


}