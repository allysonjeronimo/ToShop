package com.allysonjeronimo.toshop.view.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.allysonjeronimo.toshop.R
import com.allysonjeronimo.toshop.legacy.entities.Category
import com.allysonjeronimo.toshop.utils.resourceId
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryAdapter(
    private var categories:MutableList<Category>? = null,
    private val selectedCategoryId:Long = 0L,
    private val listener:(Category)->Unit) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    fun setList(list:MutableList<Category>){
        categories = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        var viewHolder = ViewHolder(itemView)
        viewHolder.itemView.fl_item.setOnClickListener {
            val category = categories?.get(viewHolder.adapterPosition)
            if(category != null){
                listener(category)
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories?.get(position)
        if(category != null){
            val icon = holder.itemView.context.resourceId(category.resourceIconName)
            holder.ivIcon.setImageResource(icon)
            if(category.id == selectedCategoryId){
                holder.ivCheck.visibility = View.VISIBLE
            }
            else{
                holder.ivCheck.visibility = View.GONE
            }
        }
    }

    override fun getItemCount() = categories?.size ?: 0

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var ivIcon: ImageView = itemView.iv_icon
        var ivCheck: ImageView = itemView.iv_check
    }
}
