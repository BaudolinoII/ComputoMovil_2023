package com.by_jogehrt.mygot.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.by_jogehrt.mygot.databinding.ListElementBinding
import com.by_jogehrt.mygot.info.InfoNode

class InfoAdapter(private var infoNodes : ArrayList<InfoNode>, private var onInfoClick :(inf : InfoNode) -> Unit) : RecyclerView.Adapter<InfoAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(ListElementBinding.inflate(LayoutInflater.from(parent.context)))
    override fun getItemCount(): Int = infoNodes.size
    class ViewHolder(private var binding: ListElementBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(inf : InfoNode){
            binding.tvNombre.text = inf.f_name
            binding.tvApellido.text = inf.l_name
            Glide.with(itemView.context).load(inf.image_url).into(binding.ivProfile)
        }
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val infoNode = infoNodes[position]

        holder.bind(infoNode)
        holder.itemView.setOnClickListener { onInfoClick(infoNode) }

    }


}