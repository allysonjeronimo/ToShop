package com.allysonjeronimo.toshop.view.dropdownlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.allysonjeronimo.toshop.R
import kotlinx.android.synthetic.main.item_dropdown.view.*

class DropdownListAdapter (
    var items:List<String>,
    val listener: (String) -> Unit
    ) : RecyclerView.Adapter<DropdownListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tvItem: TextView = itemView.tv_item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dropdown, parent, false)

        val viewHolder = ViewHolder(itemView)

        viewHolder.itemView.setOnClickListener {
            listener(items[viewHolder.adapterPosition])
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvItem.text = item
    }

    override fun getItemCount(): Int = items.size
}