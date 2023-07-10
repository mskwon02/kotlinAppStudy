package com.example.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.databinding.ActivityMainBinding
import com.example.recyclerview.databinding.RowBinding

class MyDataAdapter(val items:List<MyData>) : RecyclerView.Adapter<MyDataAdapter.ViewHolder>(){
    inner class ViewHolder(val binding:RowBinding):RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = RowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.textView.text=items[position].textString
        holder.binding.textView.textSize=items[position].textPt.toFloat()
    }
}