package com.by_jogehrt.mygot.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.by_jogehrt.mygot.databinding.ListElementBinding
import com.by_jogehrt.mygot.info.InfoDetail

class InfoAdapter(private var infoNodes : ArrayList<InfoDetail>, private var onInfoClick : (InfoDetail) -> Unit) : RecyclerView.Adapter<InfoAdapter.ViewHolder>() {
    class ViewHolder(private var binding: ListElementBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(inf : InfoDetail){
            binding.tvNombre.text = inf.f_name
            binding.tvApellido.text = inf.l_name
            Glide.with(itemView.context).load(inf.image_url).into(binding.ivProfile)
            if(inf.l_name?.compareTo("None") == 0 || inf.l_name?.compareTo("Unknown") == 0)
                binding.tvApellido.isInvisible = true
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val binding = ListElementBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }
    override fun getItemCount(): Int = infoNodes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val infoNode = infoNodes[position]

        holder.bind(infoNode)
        holder.itemView.setOnClickListener { onInfoClick(infoNode) }

    }


}