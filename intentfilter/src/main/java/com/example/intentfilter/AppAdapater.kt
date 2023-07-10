package com.example.intentfilter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.intentfilter.databinding.RowBinding

class AppAdapater(val items:ArrayList<MyData>):RecyclerView.Adapter<AppAdapater.ViewHolder>() {

    interface OnItemClickListener{
        fun OnItemClick(data:MyData)
    }
    var itemClickListener:OnItemClickListener?=null

    inner class ViewHolder(val binding:RowBinding):RecyclerView.ViewHolder(binding.root){
        init{
            binding.root.setOnClickListener {
                itemClickListener?.OnItemClick(items[adapterPosition])
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v=RowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.appLabel.text=items[position].appLabel
        holder.binding.appClass.text=items[position].appClass
        holder.binding.imageView.setImageDrawable(items[position].appIcon)
    }
}