package com.by_jogehrt.qrscanner

import com.by_jogehrt.qrscanner.databinding.PhoneNumberBinding
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
class PhoneBookAdapter(private var itemsArray: ArrayList<PhoneNumber>, private var onContactClicked: (PhoneNumber) -> Unit) : RecyclerView.Adapter<PhoneBookAdapter.ViewHolder>(){
    class ViewHolder(private var binding: PhoneNumberBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(phnb: PhoneNumber){
            binding.tvContactName.text = phnb.name
            binding.tvNumero.text = phnb.numberPhone
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PhoneNumberBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = itemsArray.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemPhnb = itemsArray[position]
        holder.bind(itemPhnb)

        holder.itemView.setOnClickListener {
            onContactClicked(itemPhnb)
        }

    }
}