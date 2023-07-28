package com.example.webpageparsing

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import com.example.webpageparsing.databinding.RowBinding

class MyAdapter(var items:ArrayList<MyData>)
    :RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    fun interface OnItemClickListener{
        fun onItemClick(position:Int)
    }

    var itemClickListener:OnItemClickListener?=null

    inner class MyViewHolder(val binding:RowBinding):RecyclerView.ViewHolder(binding.root){
        init{
            binding.newstitle.setOnClickListener{
                itemClickListener?.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=RowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.newstitle.text=items[position].newstitle

    }
}